package com.pickme.calendar.application.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)