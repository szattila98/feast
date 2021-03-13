package ren.practice.feast.model

import java.time.LocalDate

data class DayPlan(val date: LocalDate, val meals: List<Meal>)