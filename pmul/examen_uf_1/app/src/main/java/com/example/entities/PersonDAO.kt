package com.example.entities

import android.content.ContentValues
import com.example.database.Database

class PersonDAO {
    companion object {
        @JvmStatic
        fun insert(person: Person) {
            Database.connect { conn ->
                conn.insert("person",
                    null,
                    ContentValues().apply {
                        put("name", person.name)
                        put("age", person.age)
                        put("surname", person.surname)
                    }
                )
            }
        }
    }
}