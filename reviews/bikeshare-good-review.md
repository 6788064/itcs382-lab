# AI review of bikeshare-good (Prompt A)

AI tool: Claude, model Claude Opus 5.5 (claude-opus-5-5), 2026-10-25. The same AI also generated this code, in the same session.

1. Files read: bikeshare-good/src/main/java/bikeshare/Bike.java, BikeShareService.java, CsvRideRecorder.java, Main.java, MemberType.java, Ride.java, RideRecorder.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | BikeShareService.java:37, MemberType.java:13 | Negative minutes are accepted and give a negative fee. | Should fix | Reject minutes below 0 in giveBack. |
| 2 | 1 | Bike.java:27, BikeShareService.java:42 | The return station is not checked, so a bike returned to an unknown station can never be rented again. | Should fix | Check the station against the stations given to the constructor. |
| 3 | 1 | BikeShareService.java:42-45, CsvRideRecorder.java:20 | When the file cannot be written, the bike is already returned and the `UncheckedIOException` is not caught in Main, so the program ends. | Note | Catch the exception in Main and print a message. |
| 4 | 1 | Main.java:29, 41-42 | For an unknown member type, the user sees the Java message "No enum constant bikeshare.MemberType.VISITOR". | Note | Print a message that lists the valid types. |
| 5 | 2 | Main.java:26-44 | run has logical cohesion: the command word selects which action runs. This is normal for a menu. | Note | Keep it, or move each command into its own method. |
| 6 | 2 | Main.java:13, Bike.java:18, BikeShareService.java:26 | Connascence of meaning: stations are plain strings, so every class must spell them the same way. | Note | Use an enum Station. |
| 7 | 2 | BikeShareService.java:30 | Connascence of value: the key of `rentals` must equal the id of the Bike stored in the Rental. | Note | Accept it, because both values are set on one line. |
| 8 | 2 | CsvRideRecorder.java:17 | Connascence of name: the enum constant names STUDENT and STAFF are written into rides.csv, so renaming a constant changes the file format. | Note | Accept it, or map each type to a fixed text. |

Fields that are not private: not found. Operations that return an internal collection: not found. Common, content or control coupling: not found. Methods with procedural or weaker cohesion, other than the menu: not found.

3. Number of findings: Check 1, 4 findings. Check 2, 4 findings.
