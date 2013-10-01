/**
 * This file is released under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 **/
package clean.apk;

import java.io.File;
import java.util.concurrent.TimeUnit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.test.InstrumentationTestRunner;

public final class Clean extends InstrumentationTestRunner {

  // remove account requires API level 5
  // https://github.com/calabash/calabash-android/pull/161
  private void removeAccounts() {
    // catch android.database.sqlite.SQLiteException: cannot rollback
    // - no transaction is active (code 1)
    try {
      final AccountManager m = AccountManager.get(getTargetContext());
      for (final Account account : m.getAccounts()) {
        // catch lack of MANAGE_ACCOUNT permission
        try {
          m.removeAccount(account, null, null).getResult(20L, TimeUnit.SECONDS);;
        } catch (final Exception e) {
        }
      }
    } catch (final Exception e) {
    }
  }

  // Same method signature as Guava, different implementation.
  private static void deleteRecursively(final File file) {
    if (file == null) {
      return;
    }

    if (file.isDirectory()) {
      // listFiles is only null if file isDirectory is false.
      // directories with no files return a zero length array.
      for (final File f : file.listFiles()) {
        deleteRecursively(f);
      }
    }

    file.delete();
  }

  private static void removeParent(final File file) {
    if (file != null) {
      for (final File f : file.getParentFile().listFiles()) {
        // lib contains .so files which must not be removed.
        // Fix https://github.com/appium/clean_apk/issues/6
        if (f.isDirectory() && f.getName().contentEquals("lib")) {
          continue;
        }
        deleteRecursively(f);
      }
    }
  }

  private void removeExternal() { // API level 8 required
    final File external = getTargetContext().getExternalCacheDir();
    // Remove all external, not just the cache dir.
    removeParent(external);
  }

  private void removeInternal() { // API level 1
    final File internal = getTargetContext().getCacheDir();
    // Remove all internal, not just the cache dir.
    removeParent(internal);
  }

  public void onCreate(final Bundle arguments) {
    removeAccounts();
    removeInternal();
    removeExternal();
  }
}