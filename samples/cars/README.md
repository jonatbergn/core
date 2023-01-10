# Book a car - App

## Client & Story

The client has a pool of cars each employee can reserve. They want to handle the booking of those cars with a native
mobile app.

The client provides the list of currently available cars and their details as JSON. The backend isn't complete yet, so
any booking must get stored locally at first. Later there will be a complete backend to handle the reservations.

## Technical definitions

* Phone and tablet must be supported
* Deployment target:
  * ❌ iOS 14, Swift
  * ✅ Android: API level 31, Kotlin

## Design guidelines

* ✅ Name of the app is "CarBooking"
* ✅ AppIcon: Not defined so far.
* ❌ Colors:
  * Primary color: 172 / 207 / 204
  * Secondary color:  89 / 82 / 65
  * Accent color: 138 / 9 / 23
* ✅ Font: Use default font provided by system

## Basic app requirements

* ✅ As a user I want to see a list of all available cars, sorted alphabetically by the name.
* ✅ As a user I want to see the details for each car, including the provided image, if possible.
* ✅ As a user I want to be able to book a car in the details view.
* ✅ As a user I want to be able to set a custom booking start date and time (default is the next day at 9:00 am).
* ✅ As a user I want to be able to define the booking duration per day (minimum 1 day (24 h), maximum 7 days)
* ✅ As a user I want to see a list of all my recent and former bookings (storage local only, preparation for backend
  sync
  should be considered).
* ✅ As a user I want to see the details for each booking containing car and booking information.
* ❌ As product owner I want to have the app to be English only at first, with the option to add other languages later
  easily.

## Required Screens

* ✅ Car list
* ✅ Car details
* ✅ Booked cars
* ✅ Booking details

## API

* The list can be loaded from ```http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars.json```
* A detail can be loaded from ```http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars/{id}.json```

#### cars.json

| Parameter        | Type   | Description                        | Optional |
|:-----------------|:-------|:-----------------------------------|:--------:|
| id               | Number | Unique ID for a car.               |          |
| name             | String | The name of the car.               |          |
| shortDescription | String | A short description about the car. |    x     |

#### cars/{id}.json

| Parameter        | Type   | Description                                                                                                                                            | Optional |
|:-----------------|:-------|:-------------------------------------------------------------------------------------------------------------------------------------------------------|:--------:|
| id               | Number | Unique ID for a car.                                                                                                                                   |          |
| name             | String | The name of the car.                                                                                                                                   |          |
| shortDescription | String | A short description about the car.                                                                                                                     |    x     |
| description      | String | Full description about the car.                                                                                                                        |          |
| image            | String | Relative image path from base URL for the cars image. (i.e. http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/images/automotive.jpg) |          |

## Optional features (if there is some time left)

* ❌ Add a Pull to refresh, to load any updates from service
* ❌ A local notification should get presented when a booking starts. By tapping on it, the corresponding booking detail
  view should get shown in the app
