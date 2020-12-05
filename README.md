## Products

This is a simple CRUD application in Java based around simple products

## Getting Started

### Prerequisites

You require the Java JDK to run this app. It has been tested with the version `14.0.1`

### Run

The `go` script provides all the targets required. Run it without arguments to get the list of targets. The most common ones are:

```shell
./go build
./go run
./go test-unit
```

## Documentation

The API is documented with [Swagger](https://swagger.io/). To check it, run the application and open the [entry page](http://localhost:8080/swagger-ui/)

There is some test data created automatically to make it easier to play around.

## Architecture

It is a relatively straightforward two layer app:

- The _controller_ handles the validation of requests and calls the repository that interacts with the Database
- The _repository_ fetches and manipulates the products

The code is intended to be divided in vertical slices. Domain functionalities are grouped into its own package in a way that includes the presentation (controller), domain logic and services.

There is only one main package (`products`). Extra domain with its own REST routes would be organized in separate packages along the same lines.

### Domain

For the domain entities, they serve the dual purpose of being serialized to JSON and used with interactions with the database. As complexity grows, it would make sense to split them to avoid having too much logic in one place.

Before being inserted into the database through an update or create, there are some simple validations to prevent broken products from being introduced. The logic is not very comprehensive (duplicated product names are accepted, for instance)

### Deleted products

The products are not deleted from the database, but only marked as such. All other routes ignore those products, so from the outside it's as if they don't exist anymore.

## Known issues

- The price is being serialized in JSON as a number, even though we store it internally in `BigDecimal`. To fix that, a custom serializer is missing. It should force the price to be handled in JSON as a string to avoid losing precision.
- The Swagger overview is listing some return codes that don't actually happen due to some default configuration somewhere. 
- The validations for create and update are shared. This means that we cannot update just one field in a product. We can split the validation in two different objects or allow non-existing values for the update to prevent this.
- Listing products doesn't support pagination.

## Next steps

- Adding a pipeline to build the project automatically. Split the tests so that integration tests and unit tests are run separately. Add a linting step.
- To truly make it production ready, the app should be containerized. Moreover, we need to introduce profiles (dev, test, prod), so that we can support different configuration profiles, such as a real database instead of H2.

## License

See [LICENSE](./LICENSE)
