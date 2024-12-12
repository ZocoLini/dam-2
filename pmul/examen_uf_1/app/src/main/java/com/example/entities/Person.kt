package com.example.entities

class Person(
    name: String,
    age: Int,
    surname: String
)
{
    // Getter and Setter
    var name: String = name
        set(value) {
            field = value
        }

    var age: Int = age
        set(value) {
            field = value
        }

    var surname: String = surname
        private set(value) {
            field = value
        }

    override fun toString(): String {
        return "Person(name='$name', age=$age, surname='$surname')"
    }
}