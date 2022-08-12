package com.example.maydcodingchallenge.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shortLinkTable")
data class ShortLinkEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    val code : String,
    @ColumnInfo(name = "full_linke")
    val full_link : String,
    @ColumnInfo(name = "short_link")
    val shortLink : String,
    @ColumnInfo(name = "isCopied")
    var isCopied : Boolean
){

}