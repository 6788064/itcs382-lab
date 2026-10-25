# Lab Report, Code Quality and Design Quality

Repository URL: TODO

## Tools

| Item | Version or name |
| --- | --- |
| JDK | TODO (first line of `java -version`) |
| Maven | TODO (first line of `mvn -version`) |
| IDE | Visual Studio Code TODO (version from Help, About) |
| SonarQube for IDE | TODO (version from the Extensions view) |
| AI tool and exact model | Claude (claude.ai), model Claude Opus 5.5 (claude-opus-5-5) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 2eead81

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S2095 | Main.java:8 | Use try-with-resources or close this "Scanner" in a "finally" clause. |
| 2 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 5 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 6 | java:S1104 | Parcel.java:7 | Make status a static final constant or non-public and provide accessors if needed. |
| 7 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 8 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 9 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 10 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 11 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 12 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 13 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 14 | java:S108 | ParcelService.java:80 | Either remove or fill this block of code. |

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
| 4 | Main.java:8 | Scanner never closed | both | java:S2095 | yes, but it wraps System.in, so the leak matters little |
| 5 | ParcelService.java:6 | Unused import Date | both | java:S1128 | yes, Date is not used in the file |
| 6 | ParcelService.java:12 | Unused field maxParcels | both | java:S1068 | yes, nothing reads maxParcels |
| 7 | Parcel.java:7 | Public field status | both | java:S1104; field not private | yes, it is declared public |
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

The connascence of meaning for the parcel size, where the codes 1, 2 and 3 must mean the same in Parcel, Locker and ParcelService (row 23). Also the control coupling from the boolean `forStaff`, which Main passes to decide what printParcels does (row 20).

2.2 Name one problem that only SonarQube found, or write none.

The use of System.out instead of a logger (java:S106), at seven places in Main and ParcelService (row 8).

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The finding that pickUp has procedural cohesion (row 14) is debatable. All of its steps work on the same parcel, so it can also be called communicational, which the prompt says not to report. The level of cohesion is a judgement call, and the AI gave no reason for its choice. I checked the code findings by running the program: `pickup P1 5` gave Fee: 0.0, `store P1` crashed, and parcels.csv was empty. I checked the other findings by opening each file at the given line.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | 2cf6b8a |
| 2 | Kept | Printing is the output of a command-line program, which a logger would change. |
| 3 | Kept | Same reason as 2. |
| 4 | Kept | Same reason as 2. |
| 5 | Kept | Same reason as 2. |
| 6 | Fixed | 28e13f0 |
| 7 | Fixed | 0eae181 |
| 8 | Fixed | e7ddfaf |
| 9 | Kept | The line simulates sending the SMS on the console, so it is program output. |
| 10 | Fixed | 0de486a |
| 11 | Kept | The staff report is program output for the user. |
| 12 | Kept | The list of waiting parcels is program output for the user. |
| 13 | Fixed | a686a6c |
| 14 | Fixed | 9cfd8f7 |

Number of SonarQube issues that remain after your fixes: TODO (7 expected, all java:S106)

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
| Builds with mvn package (yes or no) | TODO | TODO |
| Number of classes, interfaces, enums and records | 2 classes | 4 classes, 1 interface, 1 enum, 2 records (Ride, and Rental nested in BikeShareService) |
| SonarQube issues, total | TODO | TODO |
| SonarQube issues, by rule | TODO | TODO |
| AI review findings in Check 1 | 6 | 4 |
| AI review findings in Check 2 | 12 | 4 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

TODO, after checking the Problems panel. SonarQube has no rule named after cohesion or coupling. The nearest rules concern the public static lists, such as java:S1444 or java:S2386. These point at the global data that causes common coupling, but they do not name it.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. The poor version got 12 design findings, 4 of them Must fix, and the good version got 4 design findings, all Notes. For example, only bikeshare-poor has control coupling, from the boolean `rent` that handle passes to process (BikeShare.java:37, 39, 53).

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

bikeshare-good, because the new type is one line, `VISITOR(4)`, in the enum MemberType, and the fee rule, the menu and the CSV file pick it up without any other change. In bikeshare-poor, I would add a new int code and change the ternary that reads the type (BikeShare.java:36) and the fee rule (BikeShare.java:71). I would also have to remember that rides.csv now holds a new code. Because of this connascence of meaning, it is easy to miss one place.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks code-level rules inside one file: probable bugs such as the String comparison with == (java:S4973) and the unclosed FileWriter (java:S2095), and code smells such as the unused import and the unused field. It does not judge design across classes. It did not report the size codes shared by four classes, the control coupling of printParcels, or ParcelService doing five jobs.

5.2 When should you not trust the AI review? Give an example from this lab.

I should not trust it on judgement calls without checking them, or when it reviews code that it wrote itself. The cohesion level of pickUp could be procedural or communicational, and the AI chose one without a reason. In Task 4 the same AI wrote and reviewed the BikeShare code, so it may have been biased toward its own good design. Every finding has to be checked against the code, or by running the program.

5.3 What will you do differently in Project Phase 2 after this lab?

I will run SonarQube for IDE while I write the code, not only at the end, and fix bugs such as == on Strings and unclosed files at once. I will also use enums instead of number codes, and avoid boolean flags that change what a method does. I will check every AI finding against the code before I put it in the report.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 2 review of the starter with Prompt A | reviews/parcelpoint-review.md | Opened each file at the given line; ran the program to confirm the == bug, the empty CSV and the crash on short input |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 3 fixes and the two design fixes | parcelpoint/src (all fixes, ParcelSize.java, ParcelCsvFile.java) | Built and ran after each fix; checked that the output and parcels.csv are correct and that the issues left the Problems panel |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Task 4 code with Prompts B and C, and Prompt A reviews of both versions. All of this ran in the same chat as Task 2 and 3, not in new chats | bikeshare-poor, bikeshare-good, reviews/bikeshare-poor-review.md, reviews/bikeshare-good-review.md | Built and ran both versions with rentals, returns, wrong input and an unknown bike; compared fees and rides.csv with the specification; checked each review finding against the code |
| Claude (claude.ai), Claude Opus 5.5 (claude-opus-5-5), 2026-10-25 | Drafted the answers in this report | LAB_REPORT.md | TODO: read and rewrite the answers in your own words, and check Table 1 against the Problems panel |
