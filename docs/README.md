# Katsu User Guide

![Katsu](docs/Ui.png)

Katsu is your personal assistant that lets you keep track of your daily and future tasks! ˗ˏˋ𓅭ˎˊ˗

## Features 🚀
### 🗓 Todo, Event, Deadline

Add different tasks easily using `todo`, `event`, or `deadline` commands.

Example:
- `todo <task's description>`
- `deadline <deadline's description> /by <yyyy-MM-dd HH:mm>`
- `event <event's description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>`

Katsu will add the specified tasks into your task list so you can easily keep track of them!

Here's an example outcome if you were to run `todo Read book`:
```
Quack! I've added the task below to your list:
[T][ ] Read book
You now have 1 task in the list.
```

### ✅ Mark and Unmark

Mark tasks as completed or unmark them using `mark` and `unmark` command.

Example:
- `mark <task's number>`
- `unmark <task's number>`

Katsu will mark the specified task in your task list.

Here'a an example outcome if you were to run `mark 1`:
```
Quack! I have marked this task as completed:
[T][X] Read book
```


### 🗑️ Delete

Remove tasks from your list using the `delete` command.

Example:
- `delete <task's number>`

Katsu will remove the specified task from your task list.

Here's an example outcome if you were to run `delete 1`:
```
Quack! I've removed the task below from your list:
[T][X] Read book
You have no more task in the list.
```

### 🔍 Find

Search for tasks containing specific keywords using the `find` command.

Example:
- `find <keywords>`

Katsu will display all tasks that match your search term.

Here's an example outcome if you were to run `find book`:
```
Quack! Here are the matching tasks in your list:
1.[T][ ] Read book
2.[T][X] Return library book
```

### 💾 Save

You tasks will be saved when you close the window or run the command `bye` which will also close the window!

The application automatically loads your saved tasks when you start it up again.

## Getting Started

1. Ensure you have Java-17 downloaded
2. Download the latest version of Katsu
3. Run the jar file using `java -jar katsu.jar `
4. Type your commands and press Enter to execute them
5. Type `bye` to exit the application

## ❓Need Help?

If you encounter any issues or have questions about using Katsu, here are some tips:
- Double-check your command format using the examples above
- Make sure date formats follow the pattern `yyyy-MM-dd HH:mm`
- Task numbers should correspond to the numbers shown in your task list

You can also refer back to this guide anytime for command references. Happy task managing! ˗ˏˋ𓅭ˎˊ