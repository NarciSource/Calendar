package com.pickme.schedule.application.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)