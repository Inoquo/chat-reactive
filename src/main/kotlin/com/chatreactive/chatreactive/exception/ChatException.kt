package com.chatreactive.chatreactive.exception

data class ChatException(val errorMessage: String) : RuntimeException(errorMessage)
