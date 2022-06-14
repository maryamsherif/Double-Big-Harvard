# Double-Big-Harvard :hamburger:
Harvard Architecture is the digital computer architecture whose design is based on the concept where there are separate storage and separate buses (signal path) for instruction and data. It was basically developed to overcome the bottleneck of Von Neumann Architecture.

<h2> Instruction Memory & Data Memory </h2>
IM Size: 1024 * 16 <br>
DM Size: 2048 * 8

<h2> Registers File </h2>
• Size: 8 bits <br>
• 64 General-Purpose Registers (GPRS) <br>
– Names: R0 to R63 <br>
• 1 Status Register <br>

<h2> Instruction Set Architecture </h2>
a) Instruction Size: 16 bits <br>
b) Instruction Types: 2 <br>
c) Instruction Count: 12 <br>
( ADD - SUB - MUL - MOVI - BEQZ - ANDI - EOR - BR - SAL - SAR - LDR - STR )

<h2> Datapath </h2>
a) Stages :  <b> 3 </b> <br>
&nbsp;&nbsp;&nbsp;  • Instruction Fetch (IF) <br>
&nbsp;&nbsp;&nbsp;  • Instruction Decode <br>
&nbsp;&nbsp;&nbsp;  • Instruction Execute <br>
b) Pipeline: <b> 3 </b> instructions (maximum) running in parallel
