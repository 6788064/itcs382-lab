# AI review of bikeshare-poor (Prompt A)

AI tool: Claude, model Claude Opus 5.5 (claude-opus-5-5), 2026-10-25. The same AI also generated this code, in the same session.

1. Files read: bikeshare-poor/src/main/java/bikeshare/Main.java, BikeShare.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | BikeShare.java:36 | Any member type other than "staff", including a typo, is silently treated as a student. | Should fix | Reject a type that is neither student nor staff. |
| 2 | 1 | BikeShare.java:39, 71 | Negative minutes are accepted and give a negative fee. | Should fix | Reject minutes below 0 before computing the fee. |
| 3 | 1 | BikeShare.java:53-81 | process is long and holds two unrelated algorithms in one if-else. | Should fix | Split it into rent and giveBack methods. |
| 4 | 1 | BikeShare.java:42 | A very long line with a nested ternary is hard to read. | Note | Move the text of the line into a helper method. |
| 5 | 1 | BikeShare.java:56, 79 | The station name is not checked, so a bike returned to an unknown station can never be rented again. | Note | Check the station against the list of stations. |
| 6 | 1 | BikeShare.java:71 | Magic numbers 3 and 2 for the fee rates. | Note | Name the rates as constants, or keep them with the member type. |
| 7 | 2 | BikeShare.java:53 | process has logical cohesion: the flag `rent` selects one of two different jobs. | Must fix | Split it into two methods. |
| 8 | 2 | BikeShare.java:37, 39 to 53 | Control coupling: handle passes the boolean `rent` that decides what process does. | Must fix | Call two separate methods. |
| 9 | 2 | BikeShare.java:37, 39, 53 | Each call passes a dummy value (type 0 or minutes 0) that the chosen branch ignores, so callers must know which parameters matter. This is connascence of position and of meaning. | Should fix | Give each method only its own parameters. |
| 10 | 2 | BikeShare.java:33-50 | handle has logical cohesion: it parses commands, runs them and prints the list of bikes. | Should fix | Move the menu into Main and the listing into its own method. |
| 11 | 2 | BikeShare.java:8-82 | Weak class cohesion: one class holds the menu handling, the fee rules, the bike records and the file saving. | Must fix | Split it into Bike, a service class, a fee rule and a file writer. |
| 12 | 2 | BikeShare.java:12-15 | Common coupling: four mutable `public static` lists are global data that any class can change. | Must fix | Make them private instance fields. |
| 13 | 2 | BikeShare.java:12-15 | Fields are not private. | Must fix | Make them private. |
| 14 | 2 | BikeShare.java:12-15, 55-59, 66-79 | Connascence of position: the four parallel lists must stay in the same order, and index i means the same bike in each. | Should fix | Replace them with a list of Bike objects. |
| 15 | 2 | BikeShare.java:9-10, 28, 36, 71 | Connascence of meaning: the int codes 1, 2 and 0 stand for student, staff and none. | Should fix | Use an enum MemberType. |
| 16 | 2 | BikeShare.java:59, 56 | Connascence of meaning: a null station means "rented". | Note | Keep an explicit rented flag in a Bike class. |
| 17 | 2 | BikeShare.java:74 | The int type code is written into rides.csv, so the file's readers must also know the code. | Note | Write the enum name instead. |
| 18 | 2 | Main.java:7, 14 | Main works through static methods on global state, which ties it to the common data of BikeShare. | Note | Create an instance of a service and call its methods. |

Content coupling: not found. Operations that return an internal collection: not found, but the collections are public anyway (see 12).

3. Number of findings: Check 1, 6 findings. Check 2, 12 findings.
