# Connect Four
This Java assignment was to implement a terminal-based version of the classic Connect Four game, where two players alternate dropping pieces into a 7-column, 6-row board in an attempt to connect four of their symbols in a row—horizontally, vertically, or diagonally.

Objectives:
- Practice writing clean and modular Java code using object-oriented principles

- Implement a turn-based game system with user input and board state management

- Design reusable classes to represent game components such as the board and players

- Develop logic to check for win conditions in multiple directions

- Write and run unit tests using JUnit to verify game functionality

Breakdown:
- Implement ConnectFourGame to manage gameplay loop, turns, and victory checks

- Use ConnectFourBoard to model the 2D grid and manage piece placement

- Create a Player class to represent each participant with a symbol (e.g., '<3' or ':)')

- Handle move validation, board updates, and player input in real-time

- Display the current board state after every move using console output

- Include ConnectFourTest with JUnit to test key methods like move placement and win detection

- Allow replay functionality and error handling for invalid inputs

