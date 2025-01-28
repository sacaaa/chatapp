# Chat Application

This is a real-time chat application where users can register, log in, and communicate within different chat rooms. The application uses **JWT authentication** for secure access, and real-time messaging is handled through **WebSockets**. The project is built with **Angular** and **Tailwind CSS** for the frontend, and **Java Spring Boot** with **Kafka** for the backend.

## Features

- **User Registration & Authentication**:
  - Secure user registration and login using JWT.
- **Chat Rooms**:
  - Users can join chat rooms and communicate in real-time.
- **Real-Time Messaging**:
  - Messages are sent and received instantly via WebSockets.
- **Modern UI**:
  - Styled with Tailwind CSS for a clean and responsive design.
- **Backend**:
  - Built with Java Spring Boot, leveraging Kafka for asynchronous processing.

---

## Technologies Used

### Frontend
- **Angular**: Framework for building the user interface.
- **Tailwind CSS**: Utility-first CSS framework for styling.

### Backend
- **Java Spring Boot**: Backend framework for API and WebSocket handling.
- **Kafka**: For message processing and communication.

### Real-Time Communication
- **WebSockets**: Used for sending and receiving real-time messages between the client and server.

---

## Installation and Setup

### Frontend (Angular)
1. Navigate to the frontend directory.
2. Install dependencies (if not already installed):
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   ng serve
   ```
4. Access the application at `http://localhost:4200`.

### Backend (Java Spring Boot)
1. Navigate to the backend directory.

2. Start the backend server:
    ```bash
   docker-compose up --build
   ```

## Usage
1. Register a new account or log in with existing credentials.
2. Browse the list of chat rooms and join one to start chatting.
3. Messages are exchanged in real-time with other users in the same chat room.