package com.rosan.installer.data.app.model.exception

class InstallFailedHyperOSIsolationViolationException : Exception {
    constructor() : super()

    constructor(message: String?) : super(message)
}