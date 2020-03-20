# Spring HATOAS Tutorial

## Tutorial Notes

Preferred to use composition (EntityModel & CollectionModel) instead of inheritance (RepresentationModel).

All the tutorials with representation models actually mutate them which I found a bit shady.  

In my stubbed service,
it caused the entities to accumulate redundant links!!

## More general HATOAS notes

### Infoq presentation: 
https://www.infoq.com/presentations/spring-hateoas-1/

Hypermedia REST style is actually NOT well supported by web frameworks.

Just went 1.0 recently.
Related to Spring Data Rest

HATEOAS
-> resource state with application data as links
  1. static 
    Related resources
    self relation

Links representing resource state should be assumed DYNAMIC!  Resources as state machine.
- affordance (eg. cancel unpaid order) is controlled by server
- client just expose & render link

Major areas where spring-hateoas helps:
1. Representation Models
2. Links & Affordances
3. Media types
4. Client Support


...2 Links & Affordances (fluent)
...3 Media Types

HAL media type format will drop affordances

Curies: documentation URLs for link types (saw example in HAL-FORMS)

HAL-FORMS can configure validation patterns on type (i.e. CreditCardNumber.REGEX)

- 2 ways
1. inline resource
  + no additional requests
  - bloated

2. separate ressource
  +cachability
  - additional requests (at first)

Media types
HAL, HAL+FORMS, ALPS, UBER, Collection+JSON, (missing JSON-lD, JSON-API)\

Easy & recommended to roll your own.  They provide a few SPI interfaces to implement.
One for JACKSON and AffordanceModelFactory.
... as Spring Factories.

...4. Client Support

Traverson library (clone from JS one)

traverson.follow("movies", "movie", "actor")

Klient: Andoid hypermedia client (EXPERIMENTAL)

Feels like GraphQL but not as performant. :-(