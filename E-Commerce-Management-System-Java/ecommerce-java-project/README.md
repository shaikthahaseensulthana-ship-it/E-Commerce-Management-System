# E-Commerce Management System (Core Java)

Console-based project based on the supplied E-Commerce Management System documentation. It uses OOP, inheritance, abstraction, polymorphism, interfaces, Java Collections, exception handling, and in-memory data storage. No database or external libraries are required.

## Requirements
- JDK 17 or newer (uses modern switch expressions)
- Terminal / Command Prompt, or an IDE such as IntelliJ IDEA, Eclipse, STS, or VS Code

## Run from terminal
From the `ecommerce-java-project` directory:

### Windows
```bat
javac -d out src\main\java\com\ecommerce\model\*.java src\main\java\com\ecommerce\payment\*.java src\main\java\com\ecommerce\service\*.java src\main\java\com\ecommerce\exception\*.java src\main\java\com\ecommerce\main\*.java
java -cp out com.ecommerce.main.ECommerceApplication
```

### macOS / Linux
```bash
javac -d out $(find src/main/java -name '*.java')
java -cp out com.ecommerce.main.ECommerceApplication
```

## Demo admin login
- Email: `admin@shop.com`
- Password: `admin123`

## Demo products
- ID 101: Laptop, Electronics, ₹55,000, stock 10
- ID 102: T-Shirt, Clothing, ₹999, stock 25
- ID 103: Rice 5kg, Grocery, ₹450, stock 30

Register a customer from the main menu to try customer features.

## Included functionality
- Customer registration/login with unique email validation
- Admin product add/view/search/update/stock update/remove
- Product categories: Electronics, Clothing, Grocery
- Cart add/remove/update, stock checks, and totals
- Optional discount from 0% to 10%
- Simulated UPI, card, and Cash on Delivery payment methods
- Order history, cancellation, stock restoration, and admin status updates
- Basic input validation and custom exception types

## Important notes
- This is an educational demo. All data is held in memory and is lost when the program exits, matching the PDF's data-storage section.
- Payments are simulated only. No real payment is collected. Do not enter real card details.
- The admin credentials are hard-coded for a college demo, not production use.
- Discount percentage is entered at checkout; the standalone “Apply Discount” menu option previews the calculation.
