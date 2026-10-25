# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/6788064/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | openjdk version "25.0.4.1" 2026-08-18 LTS (Eclipse Temurin) |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.143.1 |
| SonarQube for IDE | 6.2.0 |
| AI tool and exact model | Claude (claude.ai), model Claude Opus 5.5 (claude-opus-5-5) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 2eead81

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

Running the starter with `store P1 6501 2`, `pickup P1 5` and `staff` printed `Fee: 0.0` instead of 30.0, kept P1 as STORED with 2 free lockers, and left parcels.csv empty (0 bytes).

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-25 |
| AI tool and exact model | Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5) |
| Commit reviewed | 2eead81 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Attached the starter zip, and the AI read the four Java files from it |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | String compared with == in pickUp | both | java:S4973 | yes, running the program gave Fee: 0.0 and P1 stayed STORED |
| 2 | ParcelService.java:76 | FileWriter never closed | both | java:S2095 | yes, parcels.csv was empty after the run |
| 3 | ParcelService.java:80 | Empty catch block | both | java:S108 | yes, the block has no statement |
| 4 | Main.java:8 | Scanner never closed | AI | resource leak | yes, but it wraps System.in, so the leak matters little; SonarQube did not report it, only the Java extension did |
| 5 | ParcelService.java:6 | Unused import Date | both | java:S1128 | yes, Date is not used in the file |
| 5a | ParcelService.java:1 | Use java.time instead of java.util.Date | SonarQube | java:S2143 | not an AI finding |
| 6 | ParcelService.java:12 | Unused field maxParcels | both | java:S1068 | yes, nothing reads maxParcels |
| 7 | Parcel.java:7 | Public field status | AI | field not private | yes, it is declared public; SonarQube did not report it |
| 8 | Main.java:9, 15, 17, 22; ParcelService.java:30, 67, 69 | System.out used instead of a logger | SonarQube | java:S106 | not an AI finding |
| 9 | Main.java:12-17 | No input validation, so a short command crashes the program | AI | probable bug | yes, `store P1` throws ArrayIndexOutOfBoundsException |
| 10 | ParcelService.java:49 | pickUp returns 0 for an unknown code | AI | probable bug | yes, `pickup X 9` printed Fee: 0.0 |
| 11 | ParcelService.java:28, 44, 68 | Status strings repeated | AI | repeated literals | yes, but each appears only twice, so it is a minor note |
| 12 | ParcelService.java:53-61 | Magic numbers in the fee rule | AI | code smell | yes |
| 13 | ParcelService.java:24-37 | storeParcel has procedural cohesion | AI | procedural cohesion | yes, it stores, sends an SMS and saves in one method |
| 14 | ParcelService.java:39-50 | pickUp has procedural cohesion | AI | procedural cohesion | partly, all steps use the same parcel, so communicational cohesion can also be argued |
| 15 | ParcelService.java:64-72 | printParcels has logical cohesion via the flag forStaff | AI | logical cohesion | yes, the flag picks one of two reports |
| 16 | Main.java:6-27 | main has logical cohesion | AI | logical cohesion | yes, but this is normal for a command loop |
| 17 | ParcelService.java:9-83 | ParcelService does five jobs | AI | weak class cohesion | yes, allocation, fees, SMS, printing and file saving |
| 18 | ParcelService.java:52-62 | fee uses no attribute of its class | AI | weak class cohesion | yes, it uses only its parameters |
| 19 | ParcelService.java:28, 44, 67, 68, 78 | ParcelService writes Parcel.status directly | AI | content coupling | yes, it changes the internal data of Parcel |
| 20 | Main.java:19, 21 | Boolean forStaff passed to printParcels | AI | control coupling | yes |
| 21 | ParcelService.java:52 | fee receives the whole Parcel but uses only its size | AI | stamp coupling | yes |
| 22 | Main.java:22 | Main reaches through getLockers to Locker | AI | strong coupling (reach-through) | yes, Main filters the Locker objects itself |
| 23 | Parcel.java:6, Locker.java:5, ParcelService.java:15-17, 26, 56-61 | Size codes 1, 2, 3 shared by four classes | AI | connascence of meaning (convention) | yes, the comment on both fields shows the convention |
| 24 | Parcel.java:13, ParcelService.java:28, 44, 68 | Status strings shared by two classes | AI | connascence of meaning (convention) | yes |
| 25 | Parcel.java:9, Main.java:13 | Two String parameters in a row | AI | connascence of position | yes, but it is a weak form and only a note |
| 26 | ParcelService.java:20-22 | getLockers returns the internal list | AI | internal collection returned | yes, the list is returned without a copy |

2.1 Name two problems that only the AI review found.

One problem was the shared size codes 1, 2 and 3 in different classes (row 23). Each class had to use the same numbers for the same parcel sizes. Another problem was the boolean `forStaff` passed to `printParcels` (row 20). The value decides which report the method prints.

2.2 Name one problem that only SonarQube found, or write none.

SonarQube found the use of `System.out` instead of a logger (java:S106). It reported this issue seven times in `Main.java` and `ParcelService.java`. It also found the use of the old `java.util.Date` API (java:S2143).

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The finding that `pickUp` has procedural cohesion (row 14) is not very clear. All the steps work with the same parcel, so it could also be communicational cohesion. The AI did not explain why it chose procedural cohesion. I checked the possible bugs by running the program. `pickup P1 5` returned a fee of 0.0, `store P1` caused a crash, and `parcels.csv` remained empty. I also opened the Java files to check the other findings.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | Printing is the output of a command-line program, which a logger would change. |
| 2 | Kept | Same reason as 1. |
| 3 | Kept | Same reason as 1. |
| 4 | Kept | Same reason as 1. |
| 5 | Fixed | 0eae181 (removing the unused import of java.util.Date also removed this issue) |
| 6 | Fixed | 0eae181 |
| 7 | Fixed | e7ddfaf |
| 8 | Kept | The line simulates sending the SMS on the console, so it is program output. |
| 9 | Fixed | 0de486a |
| 10 | Kept | The staff report is program output for the user. |
| 11 | Kept | The list of waiting parcels is program output for the user. |
| 12 | Fixed | a686a6c |
| 13 | Fixed | 9cfd8f7 |

I also fixed two problems that SonarQube did not report, the Scanner that was never closed (2cf6b8a) and the public field status of Parcel (28e13f0), both found by the AI review and by the Java extension.

Number of SonarQube issues that remain after your fixes: 7, all java:S106 (Main.java:9, 15, 17, 22 and ParcelService.java:28, 60, 62), kept for the reasons above

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Connascence of meaning (convention): the int size codes 1, 2 and 3 in Parcel, Locker and ParcelService | Added the enum ParcelSize with the daily fee of each size, and used it in Parcel, Locker, ParcelService.fee and Main | b12d5e7 |
| Weak class cohesion: ParcelService also wrote the CSV file | Moved the file saving into the new class ParcelCsvFile, which Main passes to the ParcelService constructor | b953928 |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3

parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 2026-10-25 | 2026-10-25 |
| AI tool and exact model | Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5) | Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5) |
| Prompt (B or C as given, or your changed prompt in full) | B as given | C as given |
| Builds with mvn package (yes or no) | yes, at the first try | yes, at the first try |
| Number of classes, interfaces, enums and records | 2 classes | 4 classes, 1 interface, 1 enum, 2 records (Ride, and Rental nested in BikeShareService) |
| SonarQube issues, total | 13 | 4 |
| SonarQube issues, by rule | java:S106: 9, java:S2386: 4 | java:S106: 2, java:S6213: 2 |
| AI review findings in Check 1 | 6 | 4 |
| AI review findings in Check 2 | 12 | 4 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

SonarQube did not directly report any cohesion or coupling problems. It reported java:S2386 with the message "Make this member "protected"." at four lines in `BikeShare.java`. These issues came from the four public static lists. Sharing these lists creates common coupling because different parts of the program use the same data. SonarQube reported nine more issues about using `System.out` instead of a logger (java:S106). It did not directly identify the control coupling, logical cohesion, or shared type codes.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. The AI found 12 issues in the poor version in Check 2, compared with only four in the good version. For example, the poor version has control coupling because `handle` passes the boolean `rent` to `process` (BikeShare.java:37, 39, 53). This flag changes what the method does. The good version does not have this problem.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would choose `bikeshare-good` because it is easier to add a new member type. I only need to add `VISITOR(4)` to the `MemberType` enum, and the existing code can use the new type. In the poor version, I would need to add another number code and change the fee calculation and type handling. I would also need to make sure the new code is saved correctly in `rides.csv`. The good version is easier to maintain and less likely to have mistakes when new types are added.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks for coding problems, such as comparing Strings with `==` (java:S4973), not closing a `FileWriter` (java:S2095), and unused fields or imports. However, it does not directly explain the overall design of a program. For example, it did not identify the shared size codes across classes, the control coupling caused by `forStaff`, or the fact that `ParcelService` had too many responsibilities.

5.2 When should you not trust the AI review? Give an example from this lab.

I should not accept every AI finding without checking it. Some findings depend on how the code is interpreted. For example, the AI said that `pickUp` had procedural cohesion, but it could also be communicational cohesion because its steps use the same parcel. Also, in Task 4, the AI generated and reviewed both BikeShare versions in the same chat, so I should not assume its review is always correct. I checked the findings against the source code and tested the programs before accepting them.

5.3 What will you do differently in Project Phase 2 after this lab?

In Project Phase 2, I will use SonarQube while writing code instead of waiting until the end. I will fix problems such as String comparisons using `==` and files that are not closed as soon as possible. I will also use enums instead of number codes and avoid boolean flags that make methods do different things. Finally, I will check AI review findings against the actual code and test the program before using them in my report.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 2 review of the starter with Prompt A | reviews/parcelpoint-review.md | Opened each file at the given line; ran the program to confirm the == bug, the empty CSV and the crash on short input |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 3 fixes and the two design fixes | parcelpoint/src (all fixes, ParcelSize.java, ParcelCsvFile.java) | Built and ran after each fix; checked that the output and parcels.csv are correct and that the issues left the Problems panel |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 4 code with Prompts B and C, and Prompt A reviews of both versions. All of this ran in the same chat as Task 2 and 3, not in new chats | bikeshare-poor, bikeshare-good, reviews/bikeshare-poor-review.md, reviews/bikeshare-good-review.md | Built and ran both versions with rentals, returns, wrong input and an unknown bike; compared fees and rides.csv with the specification; checked each review finding against the code |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Drafted the answers to questions 2.1 to 5.3 and filled the tables of this report from my screenshots | LAB_REPORT.md | Checked Table 1, the remaining issues and the Task 4 counts against the SonarQube Problems panel; read every answer, checked it against the code and the program output, and rewrote the answers in my own words |
