package com.rosan.installer.data.app.model.exception

class InstallFailedUserRestrictedException : Exception {
    constructor() : super()

    constructor(message: String?) : super(message)
}