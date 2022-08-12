package com.example.maydcodingchallenge.data.local

import androidx.room.*

@Dao
interface ShortLinkDao {
    // adds a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortLink(shortLinkEntity: ShortLinkEntity)

    // deletes an link
    @Delete
    suspend fun deleteShortLink(shortLinkEntity: ShortLinkEntity)

    // read all the links
    @Query("Select * from shortLinkTable")
    fun getAllShortLinks(): List<ShortLinkEntity>

    //you can use this too, to delete an link by code.
    @Query("DELETE FROM shortLinkTable WHERE code = :code")
    suspend fun deleteLinkByCode(code: String)

    @Query("SELECT * from shortLinkTable WHERE code = :code")
    suspend fun findEntityByCode(code: String): List<ShortLinkEntity>
}