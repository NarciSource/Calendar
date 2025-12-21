package com.pickme.calendar.exception

import lombok.Getter

@Getter
class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
