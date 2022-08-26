package com.krasovitova.currencywallet.graph

import androidx.lifecycle.ViewModel
import com.krasovitova.currencywallet.custom_view.GraphPoint
import kotlin.random.Random

class GraphViewModel : ViewModel() {

    fun fetchGraphPoints(): List<GraphPoint> {
        // TODO mocked data
        return (0..20).map {
            GraphPoint(it, Random.nextInt(5000) + 1)
        }
    }
}