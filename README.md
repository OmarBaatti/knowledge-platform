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
| Pattern                 | Where                                                                           | Why |
|-------------------------|---------------------------------------------------------------------------------|---|
| **Factory**             | `factory/KnowledgeFactory.java`                                                 ||
| **Composite**           | `model/KnowledgeComponent.java`, `model/Category.java`,`model/Lesson.java`      ||
| **Iterator**            | `iterator/KnowledgeIterator.java` , `iterator/DepthFirstKnowledgeIterator.java` ||
| **Exception Shielding** | `exception/AppException.java`                                                   ||

### Technologies
| Technology    | Where                                                                                                                   | Notes |
|---------------|-------------------------------------------------------------------------------------------------------------------------|-------|
| **Collections**   | `Category` (`List<KnowledgeComponent>`), `DepthFirstKnowledgeIterator` (`Deque<KnowledgeComponent>`), `User` (`Set<Lesson>`) ||
| **Generics**      | `List<KnowledgeComponent>`, `Iterator<KnowledgeComponent>`, `Set<Lesson>`                                                                                                                        ||
| **Java I/O**      | `repository/KnowledgeRepository.java` `Main.java`                                                                               ||
| **Logging**       | `util/LoggerConfig.java`                                                                                                ||
| **JUnit Testing** | `src/test/java/...`                                                                                                     ||


## 3. Setup and Execution Instructions
```
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
...




Project structure...
