package com.example.travelone.fake.flight

class FakeFlightRepositoryImpl : FlightRepository {
    override suspend fun getAllFlights(): List<Flight> {
        return listOf(
            Flight(
                id = "1",
                airline = "Vietnam Airlines",
                departureCity = "Hà Nội",
                destinationCity = "Hồ Chí Minh",
                departureTime = "08:00",
                arrivalTime = "10:30",
                price = 1200000.0
            ),
            Flight(
                id = "2",
                airline = "Bamboo Airways",
                departureCity = "Đà Nẵng",
                destinationCity = "Hà Nội",
                departureTime = "13:00",
                arrivalTime = "14:30",
                price = 950000.0
            ),
            Flight(
                id = "3",
                airline = "Vietnam Airlines",
                departureCity = "Hà Nội",
                destinationCity = "Hawaii, USA",
                departureTime = "11:00",
                arrivalTime = "17:00",
                price = 2000000.0
            ),

            Flight(
                id = "4",
                airline = "Thai Airways",
                departureCity = "TP. HCM",
                destinationCity = "Bangkok, Thailand",
                departureTime = "09:00",
                arrivalTime = "11:30",
                price = 1500000.0
            ),

            Flight(
                id = "5",
                airline = "Aeroméxico",
                departureCity = "Hà Nội",
                destinationCity = "Quintana Roo, Mexico",
                departureTime = "22:00",
                arrivalTime = "06:00+1",
                price = 3500000.0
            ),

            Flight(
                id = "6",
                airline = "Avianca",
                departureCity = "Đà Nẵng",
                destinationCity = "San José, Costa Rica",
                departureTime = "14:00",
                arrivalTime = "20:00+1",
                price = 3200000.0
            ),

            Flight(
                id = "7",
                airline = "American Airlines",
                departureCity = "TP. HCM",
                destinationCity = "Florida, USA",
                departureTime = "08:00",
                arrivalTime = "18:00",
                price = 3000000.0
            ),

            Flight(
                id = "8",
                airline = "Garuda Indonesia",
                departureCity = "Hà Nội",
                destinationCity = "Bali, Indonesia",
                departureTime = "15:00",
                arrivalTime = "21:00",
                price = 1800000.0
            )

        )
    }
}