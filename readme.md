# FinTrak: A Modern Financial Tracker

### A Professional-Grade Portfolio Project for Back-End Java Developers

This repository contains the source code for **FinTrak**, a full-stack application designed to track and manage personal finances. Unlike a simple to-do list, this project is a strategic professional portfolio piece that demonstrates proficiency in modern software architecture, security, and advanced problem-solving.

## 🌟 What Problem Does It Solve?

Many personal finance applications are either monolithic and complex or too simplistic to be useful. FinTrak addresses this by providing a clean, microservices-based platform for managing personal finances. It allows users to manually track income, expenses, and budgets, and provides a consolidated dashboard for financial oversight.

The project's primary purpose is to serve as a **compelling and unique portfolio item**. It demonstrates my ability to design and implement a complex, enterprise-grade application from the ground up, with a strong emphasis on **scalability**, **security**, and **data integrity**.

## 🚀 Key Features (MVP)

* **Secure User Authentication**: A robust login and registration system using Spring Security and stateless JWT authentication.
* **Manual Transaction & Budget Entry**: Allows users to manually record income and expenses and set budgets for a clear view of their spending habits.
* **Interactive Dashboard**: Provides a consolidated view of a user's financial data with basic analytics and visualizations.
* **Third-Party API Integration**: A dedicated microservice integrates with the **Alpha Vantage API** for looking up real-time stock and cryptocurrency data.

## 💡 Architectural Decisions

FinTrak is built on a **microservices architecture** to showcase an understanding of modern, distributed systems. This approach provides several key benefits:

* **Modularity**: Each service (User, Finance, Market Data) is independent, making the system easier to develop, test, and maintain.
* **Scalability**: Services can be scaled independently based on load.
* **Resilience**: The failure of one service does not bring down the entire system.

A key architectural choice was the implementation of a **caching layer** within the Market Data Service. This was a strategic solution to overcome the **25-request-per-day rate limit** of the Alpha Vantage free API. By caching results, the application provides a seamless user experience while intelligently managing API calls, demonstrating an understanding of real-world constraints and proactive problem-solving.

## 🛠️ Technologies Used

| Component          | Technology                               | Rationale                                                                        |
| ------------------ | ---------------------------------------- | -------------------------------------------------------------------------------- |
| **Backend** | **Java 21 LTS, Spring Boot 3** | Modern, production-ready stack with new features like Virtual Threads.           |
| **Architecture** | **Spring Cloud** | Enables microservices patterns with an API Gateway and Service Discovery.        |
| **Database** | **PostgreSQL** | Robust relational database to ensure the integrity of critical financial data.   |
| **Security** | **Spring Security & JWT** | Industry-standard security framework for robust authentication and authorization. |
| **Containerization** | **Docker** | Simplifies development and ensures consistent deployments.                       |
| **Testing** | **JUnit & Mockito** | Demonstrates a commitment to code quality and test-driven development.           |
| **Frontend** | **React.js / Angular.js** (decoupled)      | Modern, single-page application framework for a dynamic and responsive UI.      |

## 🗺️ Project Roadmap

### Phase I (Complete: MVP)

* **Core Functionality**: User authentication, manual entry, and dashboard.
* **API Integration**: Implementation of a caching layer for the Alpha Vantage API.
* **Infrastructure**: Dockerized microservices and database setup.

-----

### Phase II (Future)

* **Automated Data Extraction**: Integrate with an OCR service to automatically scan and categorize financial data from receipts.
* **Predictive Analytics**: Implement machine learning models to predict spending patterns and offer personalized financial advice.
* **Advanced Tracking**: Support for investment portfolio tracking and micro-investing.

## 🤝 How to Run the Project

1.  **Clone the Repository**:

    ```bash
    git clone https://github.com/your-username/FinTrak.git
    cd FinTrak
    ```

2.  **Environment Setup**:

    * Ensure you have Docker and Docker Compose installed.
    * Create a `.env` file for your configurations, including your Alpha Vantage API key.

3.  **Start the Services**:

    ```bash
    docker-compose up --build
    ```

4.  **Access the Application**:

    * The frontend will be available at `http://localhost:3000`.
    * The API Gateway will be available at `http://localhost:8080`.

## 📄 License

This project is licensed under the MIT License.