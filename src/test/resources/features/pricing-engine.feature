# language: en
Feature: Pricing Engine – Online Shop Price Calculation
  As a shop owner
  I want a pricing engine that correctly calculates the final price
  So that customers are charged the right amount for their orders

  The formula is: finalPrice = basePrice − discount + shippingCost + tax
  Tax is applied on the discounted price (basePrice − discount).

  # ===========================================================================
  # PricingEngine – Delegation & Formula (Mockito-based unit tests)
  # ===========================================================================

  @unit @engine @delegation
  Scenario: Engine delegates discount calculation to DiscountStrategy
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 30€
    And a mocked ShippingStrategy returning 0€
    And a mocked TaxStrategy returning 0€ for discountedPrice 270€
    When the pricing engine calculates the price
    Then the DiscountStrategy should have been called with the order

  @unit @engine @delegation
  Scenario: Engine delegates shipping calculation to ShippingStrategy
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 0€
    And a mocked ShippingStrategy returning 27.50€
    And a mocked TaxStrategy returning 0€ for discountedPrice 300€
    When the pricing engine calculates the price
    Then the ShippingStrategy should have been called with the order

  @unit @engine @delegation
  Scenario: Engine delegates tax calculation to TaxStrategy
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 0€
    And a mocked ShippingStrategy returning 0€
    And a mocked TaxStrategy returning 27€ for discountedPrice 300€
    When the pricing engine calculates the price
    Then the TaxStrategy should have been called with the order and discountedPrice 300€

  @unit @engine @delegation
  Scenario: Engine passes discounted price to TaxStrategy
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 30€
    And a mocked ShippingStrategy returning 0€
    And a mocked TaxStrategy returning 0€ for discountedPrice 270€
    When the pricing engine calculates the price
    Then the TaxStrategy should have been called with discountedPrice 270€

  @unit @engine @formula
  Scenario: Engine subtracts discount from base price
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 30€
    And a mocked ShippingStrategy returning 0€
    And a mocked TaxStrategy returning 0€ for discountedPrice 270€
    When the pricing engine calculates the price
    Then the final price should be 270.00€

  @unit @engine @formula
  Scenario: Engine adds shipping cost to the result
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 0€
    And a mocked ShippingStrategy returning 27.50€
    And a mocked TaxStrategy returning 0€ for discountedPrice 300€
    When the pricing engine calculates the price
    Then the final price should be 327.50€

  @unit @engine @formula
  Scenario: Engine adds tax to the result
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 0€
    And a mocked ShippingStrategy returning 0€
    And a mocked TaxStrategy returning 27€ for discountedPrice 300€
    When the pricing engine calculates the price
    Then the final price should be 327.00€

  @unit @engine @formula
  Scenario: Engine combines discount, shipping and tax correctly
    Given an order with basePrice 300€
    And a mocked DiscountStrategy returning 30€
    And a mocked ShippingStrategy returning 27.50€
    And a mocked TaxStrategy returning 27€ for discountedPrice 270€
    When the pricing engine calculates the price
    Then the final price should be 324.50€

  # ===========================================================================
  # PricingEngine – End-to-end Integration Tests
  # ===========================================================================

  @integration @readme
  Scenario: README example – VIP customer, Drone shipping, EU destination
    Given a VIP customer order with basePrice 300€, weight 1.5 kg, EU destination, Drone shipping, on 2026-03-27T10:00
    When the pricing engine with VipDiscount, DroneShipping, EuTax calculates the price
    Then the final price should be 324.50€
    # discount = 10% × 300 = 30 | shipping = 20 + 5×1.5 = 27.50 | tax = 10% × 270 = 27

  @integration @gap1
  Scenario: Gap 1 – REGULAR customer, Standard shipping, Local tax (most basic)
    Given a REGULAR customer order with basePrice 100€, weight 1 kg, LOCAL destination, Standard shipping, on 2026-03-27T10:00
    When the pricing engine with RegularDiscount, StandardShipping, LocalTax calculates the price
    Then the final price should be 125.00€
    # discount = 0 | shipping = 5 + 1 = 6 | tax = 19% × 100 = 19

  @integration @gap2
  Scenario: Gap 2 – PREMIUM customer, Express shipping, EU tax
    Given a PREMIUM customer order with basePrice 100€, weight 1 kg, EU destination, Express shipping, on 2026-03-27T10:00
    When the pricing engine with PremiumDiscount, ExpressShipping, EuTax calculates the price
    Then the final price should be 116.50€
    # discount = 5% × 100 = 5 | shipping = 10 + 2 = 12 | tax = 10% × 95 = 9.50

  @integration @gap3
  Scenario: Gap 3 – Bulk discount, Standard shipping, Local + Luxury tax (high-value)
    Given a REGULAR customer order with basePrice 501€, weight 1 kg, LOCAL destination, Standard shipping, on 2026-03-27T10:00
    When the pricing engine with BulkDiscount, StandardShipping, CompositeTax(LocalTax+LuxuryTax) calculates the price
    Then the final price should be approximately 534.054€
    # discount = 15% × 501 = 75.15 | discountedPrice = 425.85
    # shipping = 5 + 1 = 6 | tax = 24% × 425.85 = 102.204

  @integration @gap4
  Scenario: Gap 4 – BlackFriday, Express shipping, Local + Climate tax (November + EXPRESS)
    Given a VIP customer order with basePrice 100€, weight 1 kg, LOCAL destination, Express shipping, on 2026-11-15T10:00
    When the pricing engine with BlackFridayDiscount, ExpressShipping, CompositeTax(LocalTax+ClimateTax) calculates the price
    Then the final price should be 109.20€
    # discount = 20% × 100 = 20 | discountedPrice = 80
    # shipping = 10 + 2 = 12 | tax = 19% × 80 + 2 = 17.20

  @integration @gap5
  Scenario: Gap 5 – HappyHour discount, Standard shipping, Local tax
    Given a REGULAR customer order with basePrice 100€, weight 1 kg, LOCAL destination, Standard shipping, on 2026-03-27T19:00
    When the pricing engine with HappyHourDiscount, StandardShipping, LocalTax calculates the price
    Then the final price should be 115.48€
    # discount = 8% × 100 = 8 | discountedPrice = 92
    # shipping = 5 + 1 = 6 | tax = 19% × 92 = 17.48

  @integration @gap6
  Scenario: Gap 6 – VIP, Pickup shipping, International tax (free shipping + tax-exempt)
    Given a VIP customer order with basePrice 100€, weight 0 kg, INTERNATIONAL destination, Pickup shipping, on 2026-03-27T10:00
    When the pricing engine with VipDiscount, PickupShipping, InternationalTax calculates the price
    Then the final price should be 90.00€
    # discount = 10% × 100 = 10 | shipping = 0 | tax = 0

  @integration @gap7
  Scenario: Gap 7 – REGULAR, Drone shipping, Local tax
    Given a REGULAR customer order with basePrice 100€, weight 2 kg, LOCAL destination, Drone shipping, on 2026-03-27T10:00
    When the pricing engine with RegularDiscount, DroneShipping, LocalTax calculates the price
    Then the final price should be 149.00€
    # discount = 0 | shipping = 20 + 5×2 = 30 | tax = 19% × 100 = 19

  @integration @gap8
  Scenario: Gap 8 – VIP, Drone+Christmas composite shipping, EU tax (December)
    Given a VIP customer order with basePrice 300€, weight 1.5 kg, EU destination, Drone shipping, on 2026-12-15T10:00
    When the pricing engine with VipDiscount, CompositeShipping(Drone+Christmas), EuTax calculates the price
    Then the final price should be 327.50€
    # discount = 10% × 300 = 30 | discountedPrice = 270
    # shipping = 27.50 + 3 = 30.50 | tax = 10% × 270 = 27

  @integration @gap8 @error
  Scenario: Drone+Christmas through engine with overweight throws exception
    Given a REGULAR customer order with basePrice 100€, weight 2.1 kg, LOCAL destination, Drone shipping, on 2026-12-15T10:00
    When the pricing engine with RegularDiscount, CompositeShipping(Drone+Christmas), LocalTax calculates the price
    Then an IllegalArgumentException should be thrown with a message containing "2.0"

  # ===========================================================================
  # Discount Strategies – isolated unit tests
  # ===========================================================================

  @unit @discount @regular
  Scenario Outline: RegularDiscount – always returns 0€
    Given a REGULAR customer order with basePrice <basePrice>€
    When the RegularDiscount strategy calculates the discount
    Then the discount should be 0.00€

    Examples:
      | basePrice |
      | 0.0       |
      | 50.0      |
      | 100.0     |
      | 300.0     |

  @unit @discount @premium
  Scenario: PremiumDiscount – returns 5% of base price
    Given a PREMIUM customer order with basePrice 100€
    When the PremiumDiscount strategy calculates the discount
    Then the discount should be 5.00€

  @unit @discount @premium
  Scenario: PremiumDiscount – scales with base price
    Given a PREMIUM customer order with basePrice 200€
    When the PremiumDiscount strategy calculates the discount
    Then the discount should be 10.00€

  @unit @discount @vip
  Scenario: VipDiscount – returns 10% of base price
    Given a VIP customer order with basePrice 100€
    When the VipDiscount strategy calculates the discount
    Then the discount should be 10.00€

  @unit @discount @vip
  Scenario: VipDiscount – scales with base price
    Given a VIP customer order with basePrice 300€
    When the VipDiscount strategy calculates the discount
    Then the discount should be 30.00€

  @unit @discount @blackfriday
  Scenario: BlackFridayDiscount – returns 20% in November
    Given a VIP customer order with basePrice 100€ on 2026-11-15T10:00
    When the BlackFridayDiscount strategy calculates the discount
    Then the discount should be 20.00€

  @unit @discount @blackfriday
  Scenario: BlackFridayDiscount – returns 0€ outside November
    Given a VIP customer order with basePrice 100€ on 2026-03-27T10:00
    When the BlackFridayDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @blackfriday
  Scenario: BlackFridayDiscount – applies regardless of customer type
    Given a REGULAR customer order with basePrice 100€ on 2026-11-15T10:00
    And a VIP customer order with basePrice 100€ on 2026-11-15T10:00
    When the BlackFridayDiscount strategy calculates the discount for both
    Then both discounts should be equal

  @unit @discount @bulk
  Scenario: BulkDiscount – returns 15% when basePrice exceeds 200€
    Given a REGULAR customer order with basePrice 201€
    When the BulkDiscount strategy calculates the discount
    Then the discount should be 30.15€

  @unit @discount @bulk
  Scenario: BulkDiscount – returns 0€ at exactly 200€ (exclusive threshold)
    Given a REGULAR customer order with basePrice 200€
    When the BulkDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @bulk
  Scenario: BulkDiscount – returns 0€ below 200€
    Given a REGULAR customer order with basePrice 100€
    When the BulkDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @bulk
  Scenario: BulkDiscount – scales with base price
    Given a REGULAR customer order with basePrice 500€
    When the BulkDiscount strategy calculates the discount
    Then the discount should be 75.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 8% at 18:00 (start, inclusive)
    Given a REGULAR customer order with basePrice 100€ at 18:00
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 8.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 8% at 19:30 (mid window)
    Given a REGULAR customer order with basePrice 100€ at 19:30
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 8.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 8% at 19:59 (last minute)
    Given a REGULAR customer order with basePrice 100€ at 19:59
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 8.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – scales with base price during happy hour
    Given a REGULAR customer order with basePrice 200€ at 19:30
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 16.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 0€ at 20:00 (end, exclusive)
    Given a REGULAR customer order with basePrice 100€ at 20:00
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 0€ at 17:59 (one minute before)
    Given a REGULAR customer order with basePrice 100€ at 17:59
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @happyhour
  Scenario: HappyHourDiscount – returns 0€ in the morning
    Given a REGULAR customer order with basePrice 100€ at 10:00
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be 0.00€

  @unit @discount @happyhour @boundary
  Scenario Outline: HappyHourDiscount – boundary by hour
    Given a REGULAR customer order with basePrice 100€ at <hour>:00
    When the HappyHourDiscount strategy calculates the discount
    Then the discount should be <expectedDiscount>€

    Examples:
      | hour | expectedDiscount |
      | 17   | 0.0              |
      | 18   | 8.0              |
      | 19   | 8.0              |
      | 20   | 0.0              |
      | 21   | 0.0              |

  # ===========================================================================
  # Shipping Strategies – isolated unit tests
  # ===========================================================================

  @unit @shipping @standard
  Scenario Outline: StandardShipping – 5€ base fee plus 1€/kg
    Given an order with weight <weightKg> kg and STANDARD shipping
    When the StandardShipping strategy calculates the shipping cost
    Then the shipping cost should be <expectedCost>€

    Examples:
      | weightKg | expectedCost |
      | 0.0      | 5.0          |
      | 1.0      | 6.0          |
      | 2.5      | 7.5          |
      | 10.0     | 15.0         |

  @unit @shipping @express
  Scenario Outline: ExpressShipping – 10€ base fee plus 2€/kg
    Given an order with weight <weightKg> kg and EXPRESS shipping
    When the ExpressShipping strategy calculates the shipping cost
    Then the shipping cost should be <expectedCost>€

    Examples:
      | weightKg | expectedCost |
      | 0.0      | 10.0         |
      | 1.0      | 12.0         |
      | 2.0      | 14.0         |
      | 5.0      | 20.0         |

  @unit @shipping @pickup
  Scenario: PickupShipping – always free regardless of weight
    Given an order with weight 50 kg and PICKUP shipping
    When the PickupShipping strategy calculates the shipping cost
    Then the shipping cost should be 0.00€

  @unit @shipping @drone
  Scenario Outline: DroneShipping – 20€ base fee plus 5€/kg (up to 2 kg)
    Given an order with weight <weightKg> kg and DRONE shipping
    When the DroneShipping strategy calculates the shipping cost
    Then the shipping cost should be <expectedCost>€

    Examples:
      | weightKg | expectedCost |
      | 0.0      | 20.0         |
      | 1.0      | 25.0         |
      | 2.0      | 30.0         |

  @unit @shipping @drone @boundary
  Scenario: DroneShipping – accepts exactly 2 kg (boundary)
    Given an order with weight 2.0 kg and DRONE shipping
    When the DroneShipping strategy calculates the shipping cost
    Then the shipping cost should be 30.00€

  @unit @shipping @drone @error
  Scenario: DroneShipping – rejects orders over 2 kg
    Given an order with weight 2.1 kg and DRONE shipping
    When the DroneShipping strategy calculates the shipping cost
    Then an IllegalArgumentException should be thrown with a message containing "2.0"

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 3€ surcharge in December
    Given an order placed on 2026-12-15
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 3.00€

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 3€ on December 1st
    Given an order placed on 2026-12-01
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 3.00€

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 3€ on December 31st
    Given an order placed on 2026-12-31
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 3.00€

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 0€ on November 30th
    Given an order placed on 2026-11-30
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 0.00€

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 0€ on January 1st
    Given an order placed on 2027-01-01
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 0.00€

  @unit @shipping @christmas
  Scenario: ChristmasShipping – returns 0€ in a regular month
    Given an order placed on 2026-03-27
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 0.00€

  @unit @shipping @christmas
  Scenario Outline: ChristmasShipping – applies regardless of base shipping type
    Given an order placed on 2026-12-15 with <shippingType> shipping
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 3.00€

    Examples:
      | shippingType |
      | STANDARD     |
      | EXPRESS      |
      | DRONE        |
      | PICKUP       |

  @unit @shipping @christmas
  Scenario: ChristmasShipping – flat fee, independent of weight
    Given an order placed on 2026-12-15 with weight 50 kg
    When the ChristmasShipping strategy calculates the shipping cost
    Then the shipping cost should be 3.00€

  # ===========================================================================
  # Tax Strategies – isolated unit tests
  # ===========================================================================

  @unit @tax @local
  Scenario: LocalTax – returns 19% of discounted price
    Given a LOCAL destination order with discountedPrice 100€
    When the LocalTax strategy calculates the tax
    Then the tax should be 19.00€

  @unit @tax @local
  Scenario: LocalTax – applies to discounted price, not base price
    Given a LOCAL destination order with basePrice 100€ and discountedPrice 90€
    When the LocalTax strategy calculates the tax
    Then the tax should be 17.10€

  @unit @tax @eu
  Scenario: EuTax – returns 10% of discounted price
    Given an EU destination order with discountedPrice 100€
    When the EuTax strategy calculates the tax
    Then the tax should be 10.00€

  @unit @tax @eu
  Scenario: EuTax – applies to discounted price, not base price
    Given an EU destination order with basePrice 300€ and discountedPrice 270€
    When the EuTax strategy calculates the tax
    Then the tax should be 27.00€

  @unit @tax @international
  Scenario Outline: InternationalTax – always returns 0€ (tax-exempt)
    Given an INTERNATIONAL destination order with discountedPrice <discountedPrice>€
    When the InternationalTax strategy calculates the tax
    Then the tax should be 0.00€

    Examples:
      | discountedPrice |
      | 0.0             |
      | 100.0           |
      | 500.0           |
      | 1000.0          |

  @unit @tax @luxury
  Scenario: LuxuryTax – returns 5% when basePrice exceeds 500€
    Given a LOCAL destination order with basePrice 501€ and discountedPrice 501€
    When the LuxuryTax strategy calculates the tax
    Then the tax should be 25.05€

  @unit @tax @luxury @boundary
  Scenario: LuxuryTax – returns 0€ at exactly 500€ (exclusive threshold)
    Given a LOCAL destination order with basePrice 500€ and discountedPrice 500€
    When the LuxuryTax strategy calculates the tax
    Then the tax should be 0.00€

  @unit @tax @luxury
  Scenario: LuxuryTax – returns 0€ below 500€
    Given a LOCAL destination order with basePrice 100€ and discountedPrice 100€
    When the LuxuryTax strategy calculates the tax
    Then the tax should be 0.00€

  @unit @tax @luxury
  Scenario: LuxuryTax – applies to discounted price, not base price
    Given a LOCAL destination order with basePrice 600€ and discountedPrice 510€
    When the LuxuryTax strategy calculates the tax
    Then the tax should be 25.50€

  @unit @tax @climate
  Scenario: ClimateTax – returns 2€ flat surcharge for EXPRESS shipping
    Given an order with EXPRESS shipping
    When the ClimateTax strategy calculates the tax
    Then the tax should be 2.00€

  @unit @tax @climate
  Scenario: ClimateTax – flat fee, does not scale with discounted price
    Given an order with EXPRESS shipping and discountedPrice 500€
    When the ClimateTax strategy calculates the tax
    Then the tax should be 2.00€

  @unit @tax @climate
  Scenario: ClimateTax – flat fee, does not scale with base price
    Given an order with basePrice 1000€, EXPRESS shipping, and discountedPrice 900€
    When the ClimateTax strategy calculates the tax
    Then the tax should be 2.00€

  @unit @tax @climate
  Scenario: ClimateTax – returns 0€ for STANDARD shipping
    Given an order with STANDARD shipping
    When the ClimateTax strategy calculates the tax
    Then the tax should be 0.00€

  @unit @tax @climate
  Scenario: ClimateTax – returns 0€ for DRONE shipping
    Given an order with DRONE shipping
    When the ClimateTax strategy calculates the tax
    Then the tax should be 0.00€

  @unit @tax @climate
  Scenario: ClimateTax – returns 0€ for PICKUP shipping
    Given an order with PICKUP shipping
    When the ClimateTax strategy calculates the tax
    Then the tax should be 0.00€

  @unit @tax @climate
  Scenario Outline: ClimateTax – returns 0€ for all non-EXPRESS shipping types
    Given an order with <shippingType> shipping
    When the ClimateTax strategy calculates the tax
    Then the tax should be 0.00€

    Examples:
      | shippingType |
      | STANDARD     |
      | DRONE        |
      | PICKUP       |

  # ===========================================================================
  # Composite Strategies – composition tests
  # ===========================================================================

  @unit @tax @composite
  Scenario: CompositeTaxStrategy – sums all contained strategies
    Given a composite tax of LocalTax + EuTax with discountedPrice 100€
    When the composite strategy calculates the tax
    Then the tax should be 29.00€
    # LocalTax 19% + EuTax 10% = 29€

  @unit @tax @composite
  Scenario: CompositeTaxStrategy – LocalTax + LuxuryTax for high-value order
    Given a composite tax of LocalTax + LuxuryTax for basePrice 501€ and discountedPrice 425.85€
    When the composite strategy calculates the tax
    Then the tax should be approximately 102.204€
    # LocalTax = 19% × 425.85 = 80.9115 | LuxuryTax = 5% × 425.85 = 21.2925

  @unit @tax @composite
  Scenario: CompositeTaxStrategy – works with a single strategy
    Given a composite tax of EuTax only with discountedPrice 270€
    When the composite strategy calculates the tax
    Then the tax should be 27.00€

  @unit @tax @composite
  Scenario: CompositeTaxStrategy – LuxuryTax inactive when basePrice ≤ 500€
    Given a composite tax of LocalTax + LuxuryTax for basePrice 500€ and discountedPrice 425€
    When the composite strategy calculates the tax
    Then the tax should be 80.75€
    # LocalTax = 19% × 425 = 80.75 | LuxuryTax = 0 (threshold not exceeded)

  @unit @tax @composite @climate
  Scenario: ClimateTax composed with EuTax – adds 2€ surcharge for EXPRESS
    Given a composite tax of EuTax + ClimateTax for an EXPRESS order with discountedPrice 100€
    When the composite strategy calculates the tax
    Then the tax should be 12.00€
    # EuTax = 10% × 100 = 10€ | ClimateTax = 2€

  @unit @tax @composite @climate
  Scenario: ClimateTax composed with EuTax – no surcharge for non-EXPRESS
    Given a composite tax of EuTax + ClimateTax for a STANDARD order with discountedPrice 100€
    When the composite strategy calculates the tax
    Then the tax should be 10.00€
    # EuTax = 10% × 100 = 10€ | ClimateTax = 0€

  # ===========================================================================
  # Composite Shipping Strategy – composition and drone weight-limit tests
  # ===========================================================================

  @unit @shipping @composite
  Scenario: CompositeShippingStrategy – sums Standard + ChristmasShipping in December
    Given a composite shipping of StandardShipping + ChristmasShipping for a 1 kg order in December
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 9.00€
    # StandardShipping(1 kg) = 6€ + ChristmasShipping = 3€

  @unit @shipping @composite
  Scenario: CompositeShippingStrategy – works with a single strategy
    Given a composite shipping of PickupShipping only
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 0.00€

  @unit @shipping @composite
  Scenario: CompositeShippingStrategy – Christmas surcharge inactive outside December
    Given a composite shipping of StandardShipping + ChristmasShipping for a 1 kg order in March
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 6.00€
    # StandardShipping(1 kg) = 6€ + ChristmasShipping(March) = 0€

  @unit @shipping @composite
  Scenario: CompositeShippingStrategy – Express + ChristmasShipping in December
    Given a composite shipping of ExpressShipping + ChristmasShipping for a 2 kg order in December
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 17.00€
    # ExpressShipping(2 kg) = 14€ + ChristmasShipping = 3€

  @unit @shipping @composite @drone
  Scenario: Drone + ChristmasShipping in December with valid weight
    Given a composite shipping of DroneShipping + ChristmasShipping for a 1.5 kg order in December
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 30.50€
    # DroneShipping(1.5 kg) = 27.50€ + ChristmasShipping = 3€

  @unit @shipping @composite @drone @boundary
  Scenario: Drone + ChristmasShipping at exactly 2 kg boundary
    Given a composite shipping of DroneShipping + ChristmasShipping for a 2.0 kg order in December
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 33.00€
    # DroneShipping(2 kg) = 30€ + ChristmasShipping = 3€

  @unit @shipping @composite @drone @error
  Scenario: Drone + ChristmasShipping over 2 kg throws exception
    Given a composite shipping of DroneShipping + ChristmasShipping for a 2.1 kg order in December
    When the composite strategy calculates the shipping cost
    Then an IllegalArgumentException should be thrown with a message containing "2.0"

  @unit @shipping @composite @drone
  Scenario: Drone + ChristmasShipping outside December adds no surcharge
    Given a composite shipping of DroneShipping + ChristmasShipping for a 1 kg order in March
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 25.00€
    # DroneShipping(1 kg) = 25€ + ChristmasShipping(March) = 0€

  @unit @shipping @composite
  Scenario: Pickup + ChristmasShipping in December – only surcharge applies
    Given a composite shipping of PickupShipping + ChristmasShipping for an order in December
    When the composite strategy calculates the shipping cost
    Then the shipping cost should be 3.00€
    # PickupShipping = 0€ + ChristmasShipping = 3€

