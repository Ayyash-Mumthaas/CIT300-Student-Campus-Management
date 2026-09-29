# CIT300 Student Campus Management System

## Project Overview

The **CIT300 Student Campus Management System** is a Java-based console application developed for the Data Structures and Algorithms module.

The system demonstrates the practical use and integration of multiple data structures to manage university student information, student service requests, recent actions, student searching, and campus locations.

The application integrates:

- Linked List
- Stack
- Queue
- Binary Search Tree (BST)
- Hash Table
- Graph
- Breadth-First Search (BFS)

The project focuses on demonstrating how different data structures can be selected and integrated according to the type of data and operation being performed.

---

## Objectives

The main objectives of the project are to:

1. Implement fundamental data structures using Java.
2. Apply data structures to realistic university management scenarios.
3. Demonstrate searching, insertion, deletion, updating, ordering, and traversal operations.
4. Demonstrate the differences between LIFO and FIFO processing.
5. Demonstrate student searching using both a Binary Search Tree and Hash Table.
6. Represent campus locations and connections using a graph.
7. Perform Breadth-First Search (BFS) on the campus graph.
8. Integrate multiple data structures within a single Java application.
9. Implement input validation and error handling.
10. Demonstrate the practical application of data structures through a working console system.

---

## Main Features

### Student Records

The Student Records module provides functionality to:

- Add students
- Update student information
- Delete students
- Search for students
- Display all students
- Validate Student IDs
- Validate marks

Student records are primarily maintained using a **Linked List**.

---

### Recent Actions

The Recent Actions module maintains a history of recent system actions.

It supports:

- Displaying recent actions
- Removing the most recent action record

The module demonstrates the **Last-In, First-Out (LIFO)** principle using a Stack.

> The action stack is a history mechanism. Removing an action from the stack does not undo the original student operation.

---

### Service Requests

The Service Requests module manages student service requests.

It supports:

- Adding service requests
- Displaying waiting requests
- Processing the next request

The module demonstrates the **First-In, First-Out (FIFO)** principle using a Queue.

---

### Student Search Tree

The Student Search Tree organizes students according to their Student IDs using a **Binary Search Tree (BST)**.

It supports:

- Displaying students in ascending Student ID order
- Searching for students by Student ID

An in-order traversal is used to display the students in sorted Student ID order.

---

### Student ID Hash Table

The Hash Table module uses Student IDs as keys for student searching.

It supports:

- Displaying hash table buckets
- Searching for students by Student ID

The implementation uses:

- Hashing
- Buckets
- Separate chaining

Separate chaining allows multiple entries to be maintained within a bucket when hash collisions occur.

---

### Campus Map

The Campus Map module represents university locations and their connections using a **Graph**.

It supports:

- Adding locations
- Removing locations
- Adding connections
- Removing connections
- Displaying the campus network
- Performing Breadth-First Search (BFS)

The graph is represented using an **adjacency list**.

Campus connections are represented as connections between locations.

---

### Breadth-First Search

The Campus Map supports **Breadth-First Search (BFS)**.

BFS can be started from a selected campus location and produces a traversal path through the connected locations.

This demonstrates graph traversal using the campus map.

---

## Data Structures Used

| Data Structure     | Application Usage                     | Main Demonstration                      |
| ------------------ | ------------------------------------- | --------------------------------------- |
| Linked List        | Primary student record storage        | Add, update, delete, search and display |
| Stack              | Recent action history                 | LIFO                                    |
| Queue              | Student service requests              | FIFO                                    |
| Binary Search Tree | Student ID organization and searching | In-order traversal and search           |
| Hash Table         | Student ID hashing and searching      | Hash buckets and separate chaining      |
| Graph              | Campus locations and connections      | Adjacency list                          |
| BFS                | Campus graph traversal                | Breadth-first traversal                 |

---

## Linked List

The `StudentLinkedList` is the primary data structure used for storing student records.

Each student is represented through a linked-list node.

The linked list supports operations including:

- Insertion
- Searching
- Updating
- Deletion
- Displaying student records

The linked list acts as the primary student store used by the application.

---

## Stack

The Stack is used to maintain recent action history.

The stack follows the:

**LIFO - Last-In, First-Out**

principle.

For example, if actions occur in the following order:

```text
Added S001
Added S002
Added S003

```

the most recent action, `Added S003`, is accessed first.

The Stack is used for demonstrating recent action history and is not implemented as an automatic undo mechanism.

---

## Queue

The Queue is used to manage student service requests.

The queue follows the:

**FIFO - First-In, First-Out**

principle.

For example:

```text
S001 - Transcript
S002 - ID Card

```

If both requests are waiting, the S001 request is processed first because it was added first.

---

## Binary Search Tree

The Binary Search Tree organizes student records according to their Student IDs.

The BST supports:

- Student ID insertion
- Student ID searching
- In-order traversal

An in-order traversal produces Student IDs in ascending order.

For example:

```text
S001
S002
S003

```

This provides an ordered representation of the student records.

---

## Hash Table

The Hash Table uses Student IDs as keys.

The implementation divides the table into buckets and calculates a hash index from the Student ID.

The implementation uses **separate chaining** to handle hash collisions.

The Hash Table supports:

- Displaying buckets
- Searching for students by Student ID

---

## Graph

The campus map is represented using a graph.

In the graph:

- Campus locations represent vertices.
- Connections between locations represent edges.
- An adjacency list is used to represent the graph.

Example campus locations include:

```text
Main Gate
Library
Cafeteria
Lecture Hall
Laboratory
Administration

```

Example connections:

```text
Main Gate <-> Library
Main Gate <-> Administration
Library <-> Cafeteria
Library <-> Lecture Hall
Lecture Hall <-> Laboratory

```

---

## Breadth-First Search

Breadth-First Search is implemented for traversing the campus graph.

For example, starting from:

```text
Main Gate

```

the application can produce a traversal such as:

```text
Main Gate -> Administration -> Library -> Lecture Hall -> Cafeteria -> Laboratory

```

The exact traversal order depends on the adjacency-list structure used by the implementation.

---

## Integration of Data Structures

One of the main features of the project is the integration of multiple data structures.

The `StudentLinkedList` acts as the primary student store.

The Binary Search Tree and Hash Table reference the same `Student` objects.

Therefore, when a student's information is updated, the updated information is reflected when the student is accessed through the other student-related structures.

When a student is deleted, the application rebuilds the Binary Search Tree and Hash Table using the remaining student records.

This allows the application to maintain consistency between the student-related data structures.

---

## Validation

The application includes validation for several types of invalid input.

Examples include:

### Duplicate Student ID

The application prevents duplicate Student IDs.

```text
Student ID already exists. Duplicate IDs are not allowed.

```

### Invalid Marks

Marks must be between 0 and 100.

```text
Marks must be between 0 and 100.

```

### Missing Student

Searching for a Student ID that does not exist returns an appropriate not-found result.

### Empty Stack

The application handles attempts to access an empty action stack.

### Empty Queue

The application handles attempts to process a request when the service queue is empty.

### Invalid Graph Location

The application validates locations before performing graph operations such as BFS.

### Invalid Menu Input

The application handles non-numeric and unsupported menu choices.

---

## Application Menu

The main application menu is:

```text
University Student Record and Campus Routes

1. Student records (Linked List)
2. Recent actions (Stack)
3. Service requests (Queue)
4. Student search tree (BST)
5. Student ID hash table
6. Campus map (Graph)
0. Exit

```

---

## How to Run

### Requirements

- Java Development Kit (JDK)
- Java command-line environment
- Git (optional, for cloning the repository)

---

### Clone the Repository

```bash
git clone https://github.com/Ayyash-Mumthaas/CIT300-Student-Campus-Management.git

```

Navigate into the project directory:

```bash
cd CIT300-Student-Campus-Management

```

---

### Compile

Compile the Java source files into the `bin` directory.

From the project root, the application can be compiled using:

```bash
javac -d bin $(find src -name "*.java")

```

On Windows PowerShell, an equivalent approach can be used to compile the Java source files into `bin`.

---

### Run

After compilation, run:

```bash
java -cp bin cit300.app.Main

```

The application will display the main menu.

---

## Project Structure

```text
CIT300-Student-Campus-Management/
│
├── .classpath
├── .gitignore
├── .project
│
├── src/
│   └── cit300/
│       ├── app/
│       │   └── Main.java
│       │
│       └── ...
│
└── bin/
    └── ...

```

The `src` directory contains the Java source code.

The `bin` directory contains compiled Java class files.

The main application entry point is:

```text
src/cit300/app/Main.java

```

---

## Demonstration Dataset

The following student records can be used to demonstrate the application:

| Student ID | Name    | Programme  | Marks |
| ---------- | ------- | ---------- | ----- |
| S001       | M.M.M. Ayyash | Applied IT | 85    |
| S002       | M.A.J Hafsa | Applied IT | 78    |
| S003       | M.T Aathifa | Applied IT | 92    |

These records can be used to demonstrate the Linked List, BST, Hash Table, update functionality, and integrated student searching.

---

## Example Campus Graph

A demonstration campus graph can contain:

```text
Main Gate
Library
Cafeteria
Lecture Hall
Laboratory
Administration

```

Connections:

```text
Main Gate <-> Library
Main Gate <-> Administration
Library <-> Cafeteria
Library <-> Lecture Hall
Lecture Hall <-> Laboratory

```

This graph can be used to demonstrate the adjacency list and BFS traversal.

---

## Testing

The application was tested for:

- Student insertion
- Student searching
- Student updating
- Student deletion
- Duplicate Student ID validation
- Marks validation
- Recent action stack operations
- Empty stack handling
- Service request queue operations
- Empty queue handling
- BST traversal
- BST searching
- Hash table bucket display
- Hash table searching
- Campus location management
- Campus connections
- BFS traversal
- Invalid BFS starting locations
- Invalid menu input

---

## Team Members

> The team information below reflects the project development assignments and student details provided by the team.

| Team Member | Student ID | Responsibility | GitHub Username |
|---|---|---|---|
| M.M.M. Ayyash | 23DA2-1033 | Stack, Queue, Main Menu/Integration, project leadership, final coordination and submission | Ayyash-Mumthaas |
| M.A.J Hafsa | 23DA2-1058 | Binary Search Tree (BST) and Hash Table/Hashing | hafsa-jinnah |
| M.T Aathifa | 23DA2-0916 | Student class, Linked List and Student Record Management | aathifathaslim |
| M.F.F Aaysha | 23DA2-0834 | Campus Graph and BFS/DFS traversal | ayshxa |

---

## Individual Contributions

### M.M.M. Ayyash — 23DA2-1033

- Implemented the Stack for recent action history.
- Implemented the Queue for student service requests.
- Integrated the team components into the main application.
- Coordinated the final project integration and testing.
- Prepared the final GitHub repository and README.
- Coordinated the demonstration video and final submission.

### M.A.J Hafsa — 23DA2-1058

- Implemented the Binary Search Tree (BST) for Student ID organization and searching.
- Implemented the Hash Table for efficient Student ID searching.
- Implemented duplicate Student ID handling and hash collision handling.
- Tested BST insertion, search, in-order traversal and duplicate ID handling.
- Tested Hash Table insertion, search, duplicate ID handling and collision handling.

### M.T Aathifa — 23DA2-0916

- Created the Student class used across the integrated system.
- Implemented the StudentNode and StudentLinkedList structures.
- Implemented student insertion, updating, deletion, searching and display operations.
- Implemented validation for duplicate IDs, invalid marks, missing students and empty lists.
- Tested the Student Records and Linked List functionality.

### M.F.F Aaysha — 23DA2-0834

- Implemented the campus Graph using an adjacency list.
- Implemented campus location and connection management.
- Implemented graph traversal using BFS/DFS as required by the project.
- Tested location management, connection management, duplicate handling and graph traversal.
- Prepared the campus graph component for integration with the main menu.

> Individual contributions above are based on the responsibilities assigned to each team member in the project development notes.

---

## Technologies Used

- Java
- Java Collections/standard Java functionality where applicable
- Git
- GitHub
- Java command-line environment

---

## Repository

GitHub repository:

https://github.com/Ayyash-Mumthaas/CIT300-Student-Campus-Management

Main branch:

```text
main

```

Latest verified integration commit:

```text
94e635c - Integrate campus management system

```

---

## Academic Project

This project was developed as part of:

**CIT300 - Data Structures and Algorithms**

The project demonstrates the practical implementation and integration of fundamental data structures within a Java application.