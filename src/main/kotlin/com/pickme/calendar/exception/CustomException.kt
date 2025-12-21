package com.pickme.calendar.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
