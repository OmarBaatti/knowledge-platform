# Knowledge Platform

A Java SE console application for browsing and searching a hierarchical, file-based knowledge base of markdown lessons (e.g. java/collections.md , java/abstract_classes.md) organized into nested categories and subcategories.

## 1. Application Overview and Functionality
The platform loads a directory tree of Markdown files at startup (e.g. knowledge/java/oop.md) and builds a tree where folders become categories and `.md` files become lessons.
The user can then interactively search the entire tree by keyword from the command line, and view matching lesson content directly in the console.

Core capabilities:
- Loads a nested folder structure of Markdown files into a tree of custom objects at startup.
- Traverse that tree via a custom depth-first iterator that uses a stack.
- Interactively search lessons/categories by keyword (case-insensitive, sanitized input) via a CLI loop
- Fail safely on bad input or I/O errors without crashing or leaking internal details to the console

## 2. Technologies and Patterns Used
### Design Patterns
| Pattern                 | Where                                                                 | Why                                                                                                                                                                                                                                     |
|-------------------------|-----------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Factory**             | `factory/KnowledgeFactory.java`                                       | Centralizes and validates creation of `Category`/`Lesson`, so invalid knowledge objects (blank names, empty content) can never be constructed from anywhere in the app.                                                                 |
| **Composite**           | `model/KnowledgeComponent.java`, `Category.java`, `Lesson.java`       | Categories and lessons need to be treated uniformly (display, iterate) despite categories being containers and lessons being leaves. Composite lets `Main`/`SearchService` work with one type instead of branching on tree versus leaf. |
| **Iterator**            | `iterator/KnowledgeIterator.java`, `DepthFirstKnowledgeIterator.java` | Separates the traversal logic from the Category class, making it easier to iterate through the knowledge tree and allowing different traversal strategies to be added in the future if needed.                                          |
| **Exception Shielding** | `exception/AppException.java` + subclasses                            | Low-level failures (`IOException`, bad input) are translated into application specific exceptions with safe messages, so `Main` never leaks stack traces or filesystem details to the console.                                          |

### Technologies
| Technology        | Where                                                                                                    | Notes                                                                                                                                            |
|-------------------|----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| **Collections**   | `Category` (`List<KnowledgeComponent>`), `DepthFirstKnowledgeIterator` (`Deque`), `User` (`Set<Lesson>`) | `Deque` used as a stack for DFS traversal; `Set` avoids duplicate lesson completions.                                                            |
| **Generics**      | `List<KnowledgeComponent>`, `Iterator<KnowledgeComponent>`, `Set<Lesson>`                                | Currently limited to generic standard library collections; no custom generic type/method yet (see Limitations).                                  |
| **Java I/O**      | `repository/KnowledgeRepository.java`                                                                    | Uses NIO.2 (`Files`, `Path`, `Files.list`, `Files.readString`) to recursively load the folder tree into `Category`/`Lesson` objects.             |
| **Logging**       | `util/LoggerConfig.java`                                                                                 | Central `java.util.logging.Logger` used across `Main`, `KnowledgeRepository`, `SearchService`, `AppConfig` for startup, load, and search events. |
| **JUnit Testing** | `src/test/java/...`                                                                                      | Unit tests cover `KnowledgeFactory`, `DepthFirstKnowledgeIterator`, `SearchService`, `KnowledgeRepository`, and `InputValidator`.                |

## 3. Setup and Execution Instructions
```bash
git clone https://github.com/OmarBaatti/knowledge-platform.git
cd knowledge-platform

./gradlew test
./gradlew run

# Knowledge env path
$env:DEENCORD_KNOWLEDGE_PATH="C:\path\to\knowledge" # Windows Powershell
export DEENCORD_KNOWLEDGE_PATH=/absolute/path/to/knowledge # MacOS/Linux 
./gradlew run
```

## 4. UML Diagrams
---- class + architectural ----
pending...

## 5. Known Limitations and Future Work
- Single threaded
- No markdown rendering
- CLI-only Interface
- No use of User and Bookmark
- Author attribute for lessons is unused



## 6. Project structure
```
src/main/java/com/deencord/
├── Main.java                # Entry point, CLI loop
├── exception/               # AppException hierarchy
├── factory/                 # KnowledgeFactory (Factory pattern)
├── iterator/                # KnowledgeIterator, DepthFirstKnowledgeIterator (Iterator pattern)
├── model/                   # KnowledgeComponent, Category, Lesson, User, Bookmark (Composite pattern)
├── repository/              # KnowledgeRepository (Java I/O, filesystem loading)
├── service/                 # KnowledgeService, SearchService, UserService
└── util/                    # InputValidator, AppConfig, LoggerConfig
```