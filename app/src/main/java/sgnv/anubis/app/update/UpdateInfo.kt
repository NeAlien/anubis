package sgnv.anubis.app.update

data class UpdateInfo(
    val latestVersion: String,
    val currentVersion: String,
    val releaseUrl: String,
    val apkUrl: String?,
    val releaseNotes: String,
) {
    val isUpdateAvailable: Boolean get() = compareVersions(latestVersion, currentVersion) > 0

    companion object {
        /**
         * Compare versions like "0.1.4-beta.1" > "0.1.3".
         * Strips pre-release suffixes (everything after first '-') before numeric comparison.
         * Returns 1, 0, or -1.
         */
        fun compareVersions(a: String, b: String): Int {
            fun numericParts(v: String): List<Int> =
                v.trimStart('v').substringBefore('-').split('.').map { it.toIntOrNull() ?: 0 }

            val aParts = numericParts(a)
            val bParts = numericParts(b)
            val len = maxOf(aParts.size, bParts.size)
            for (i in 0 until len) {
                val ai = aParts.getOrElse(i) { 0 }
                val bi = bParts.getOrElse(i) { 0 }
                if (ai != bi) return ai.compareTo(bi)
            }
            return 0
        }
    }
}
