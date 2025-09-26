package com.rosan.installer.util

import java.util.Locale

/**
 * Converts legacy Android language codes to their modern equivalents.
 * This ensures compatibility with older and non-standard APK splits.
 *
 * Logic is from the PackageUtil.kt file in the PackageInstaller project.
 * Under Apache License 2.0.
 *
 * @param this The legacy language code to be converted.
 * @return The modern language code.
 * @see <a href="https://developer.android.com/reference/java/util/Locale#legacy-language-codes">java.util.Locale#legacy-language-codes</a>
 *  * @see <a href="https://github.com/vvb2060/PackageInstaller/blob/master/app/src/main/java/io/github/vvb2060/packageinstaller/model/PackageUtil.kt">PackageUtil</a>
 * @see <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a>
 */
fun String.convertLegacyLanguageCode(): String =
    when (this) {
        "in" -> "id" // Indonesian
        "iw" -> "he" // Hebrew
        "ji" -> "yi" // Yiddish
        "tl" -> "fil" // Tagalog -> Filipino
        else -> this
    }

fun isLanguageCode(code: String): Boolean {
    val locale = Locale.forLanguageTag(code)
    return locale.language.isNotEmpty() &&
            locale.displayLanguage.isNotEmpty() &&
            locale.displayLanguage.lowercase() != locale.language
}