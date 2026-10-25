# AI review of parcelpoint (Prompt A)

AI tool: Claude, model Claude Opus 5.5 (claude-opus-5-5), 2026-10-25. Files given as an attached zip and read from disk. Commit reviewed: 2eead81.

1. Files read: parcelpoint/src/main/java/parcelpoint/Locker.java, Main.java, Parcel.java, ParcelService.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | ParcelService.java:42 | `parcel.getCode() == code` compares String objects by reference, so a code typed by the user never matches the stored code: pickUp returns 0, the locker is never freed and the status stays STORED. | Must fix | Compare with `parcel.getCode().equals(code)`. |
| 2 | 1 | ParcelService.java:76 | The `FileWriter` is never closed or flushed, so parcels.csv stays empty and a file handle leaks on every save. | Must fix | Open the writer in a try-with-resources statement. |
| 3 | 1 | ParcelService.java:80-81 | Empty `catch (IOException e)` block hides every failure to save. | Should fix | Report the error or rethrow it as an `UncheckedIOException`. |
| 4 | 1 | Main.java:8 | The `Scanner` is never closed. | Note | Open the Scanner in a try-with-resources statement. |
| 5 | 1 | ParcelService.java:6 | Unused import `java.util.Date`. | Should fix | Remove the import. |
| 6 | 1 | ParcelService.java:12 | Private field `maxParcels` is never read, so the limit of 100 parcels is never enforced. | Should fix | Remove the field, or check it in storeParcel. |
| 7 | 1 | Main.java:12-17 | Input is not validated: a short command throws `ArrayIndexOutOfBoundsException` and a non-number throws `NumberFormatException`, which ends the program. | Should fix | Check the number of words and catch `NumberFormatException` before calling the service. |
| 8 | 1 | ParcelService.java:49 | pickUp returns 0 when no parcel has the code, which looks the same as a free pickup. | Should fix | Return an empty `OptionalDouble` or print "Parcel not found" for an unknown code. |
| 9 | 1 | ParcelService.java:28, 44, 68; Parcel.java:13 | The status literals "NEW", "STORED" and "PICKED_UP" are repeated as strings in two classes. | Note | Replace them with an enum. |
| 10 | 1 | ParcelService.java:53-61 | Magic numbers 3, 10.0, 15.0 and 20.0 in the fee rule. | Note | Name them as constants or keep them with the size. |
| 11 | 1 | Main.java:6-27 | main mixes reading, parsing and five commands in one if-else chain; it is readable now but grows with every command. | Note | Move each command into its own method. |
| 12 | 2 | ParcelService.java:24-37 | storeParcel has procedural cohesion: it finds a locker, records the parcel, sends the SMS and saves the file, steps linked only by the order in which they run. | Should fix | Move the SMS and the file saving out to their own classes. |
| 13 | 2 | ParcelService.java:39-50 | pickUp has procedural cohesion: it frees the locker, saves the file and computes the fee in one method. | Note | Move the saving out of the method. |
| 14 | 2 | ParcelService.java:64-72 | printParcels has logical cohesion: the flag `forStaff` selects one of two different reports. | Should fix | Split it into printStaffReport and printWaitingParcels. |
| 15 | 2 | Main.java:6-27 | main has logical cohesion: one method runs every command, chosen by the first word. | Note | Keep the dispatch, but move each command into its own method. |
| 16 | 2 | ParcelService.java:9-83 | Weak class cohesion: ParcelService allocates lockers, sets the fee policy, sends SMS, prints reports and writes the CSV file. | Should fix | Extract the file saving and the printing into their own classes. |
| 17 | 2 | ParcelService.java:52-62 | fee does not use any attribute of ParcelService, only its parameters. | Note | Move the fee rate next to the parcel size. |
| 18 | 2 | ParcelService.java:28, 44, 67, 68, 78 | Content coupling: ParcelService reads and writes the field `status` of Parcel directly. | Must fix | Make status private and add methods such as markStored and markPickedUp. |
| 19 | 2 | Main.java:19, 21 to ParcelService.java:64 | Control coupling: Main passes the boolean `forStaff` that decides what printParcels does. | Should fix | Split printParcels into two methods. |
| 20 | 2 | ParcelService.java:52 | Stamp coupling: fee receives the whole Parcel but uses only its size. | Note | Pass only the size. |
| 21 | 2 | Main.java:22 | Main reaches through ParcelService to the list of Locker objects and filters them itself, so Main depends on Locker. | Should fix | Add a method `countFreeLockers()` to ParcelService. |
| 22 | 2 | Parcel.java:6, Locker.java:5, ParcelService.java:15-17, 26, 56-61, Main.java:9 | Connascence of meaning (convention): the size codes 1, 2 and 3 must mean small, medium and large in four classes. | Should fix | Replace the int code with an enum ParcelSize. |
| 23 | 2 | Parcel.java:13, ParcelService.java:28, 44, 68 | Connascence of meaning (convention): the status strings must be spelled the same in two classes. | Should fix | Replace the strings with an enum ParcelStatus. |
| 24 | 2 | Parcel.java:9, Main.java:13 | Connascence of position: the constructor takes two Strings, code then studentId, which a caller can swap without a compile error. | Note | Accept it for two parameters, or use a small factory method with named parameters. |
| 25 | 2 | Parcel.java:7 | Field `status` is public. | Must fix | Make it private. |
| 26 | 2 | ParcelService.java:20-22 | getLockers returns the internal list, so callers can add or remove lockers. | Should fix | Return `Collections.unmodifiableList(lockers)`, or replace the method with countFreeLockers. |

Common coupling: not found. Classes that depend on many other classes: not found, as no class depends on more than three others.

3. Number of findings: Check 1, 11 findings. Check 2, 15 findings.
