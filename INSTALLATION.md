## Installation Instructions

### Prerequisites
- **Java Development Kit (JDK)** 8 or above.
- **Maven** (build automation tool).
- **Git** (to clone the repository).
- **IntelliJ IDEA** or any IDE that supports Maven projects.

### Steps for Installation

#### Windows
1. **Install Dependencies**:
    - Download and install [JDK](https://www.oracle.com/java/technologies/javase-downloads.html).
    - Install [Maven](https://maven.apache.org/install.html) and ensure it is added to your system PATH.
    - Install [Git](https://git-scm.com/) if not already installed.
2. **Clone the Repository**:
    ```
    git clone https://github.com/<your-username>/<repository-name>.git
    cd <repository-name>
    ```
3. **Open in IntelliJ IDEA**:
    - Open IntelliJ and select "Open or Import".
    - Navigate to the cloned folder and select the `pom.xml` file.
    - Wait for Maven dependencies to download.
4. **Run the Application**:
    - Locate the `Main` class (`org.example.Main`).
    - Right-click and select "Run Main".

#### macOS
1. **Install Dependencies**:
    - Install Homebrew:
      ```
      /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
      ```
    - Use Homebrew to install JDK, Maven, and Git:
      ```
      brew install openjdk maven git
      ```
    - Add the JDK to your PATH. Example for OpenJDK 17:
      ```
      export PATH="/usr/local/opt/openjdk/bin:$PATH"
      ```
2. **Clone the Repository**:
    ```
    git clone https://github.com/<your-username>/<repository-name>.git
    cd <repository-name>
    ```
3. **Open in IntelliJ IDEA**:
    - Open IntelliJ and select "Open or Import".
    - Navigate to the cloned folder and select the `pom.xml` file.
    - Wait for Maven dependencies to download.
4. **Run the Application**:
    - Locate the `Main` class (`org.example.Main`).
    - Right-click and select "Run Main".

#### Linux
1. **Install Dependencies**:
    - Install JDK, Maven, and Git via your package manager:
      ```
      sudo pacman -Syu update
      sudo pacman -S jdk21-openjdk maven git
      ```
      (Replace `openjdk-17-jdk` with your desired JDK version.)
2. **Clone the Repository**:
    ```
    git clone https://github.com/<your-username>/<repository-name>.git
    cd <repository-name>
    ```
3. **Open in IntelliJ IDEA**:
    - Open IntelliJ and select "Open or Import".
    - Navigate to the cloned folder and select the `pom.xml` file.
    - Wait for Maven dependencies to download.
4. **Run the Application**:
    - Locate the `Main` class (`org.example.Main`).
    - Right-click and select "Run Main".

---

### Assets
you will need to get the big .obj files your self and adjust the compensation vectors and scale in the corresponding Furniture implementations.
- **objects/**: Contains `.obj` files for furniture models.
- **Textures/**: Contains texture images used for materials (e.g., `floor.png`, `wall_carpet.png`).