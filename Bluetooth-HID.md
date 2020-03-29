# Bluetooth HID Information

## Output reports

#### Standard input report format

Taken from: https://github.com/dekuNukem/Nintendo_Switch_Reverse_Engineering/blob/master/bluetooth_hid_notes.md

Did not match inputs I recieved. Might be different. Still helped

(Note: in the following table, the byte with the packet ID is included and located at byte "0".)

|   Byte #           |        Sample         | Remarks                                                                             |
|:------------------:|:---------------------:| ----------------------------------------------------------------------------------- |
| 1                  | `x21`, `x30`, `x31`   | Input report ID                                                                     |
| 2                  | `x00` - `xFF`         | Timer. Increments very fast. Can be used to estimate excess Bluetooth latency.      |
| 3 high nibble      | `0` - `9`             | Battery level. 8=full, 6=medium, 4=low, 2=critical, 0=empty. LSB=Charging.          |
| 3 low nibble       | `x0`, `x1`, `xE`      | Connection info. `(con_info >> 1) & 3` - 3=JC, 0=Pro/ChrGrip. `con_info & 1` - 1=Switch/USB powered. |
| 4, 5, 6            | `x41 00 82`           | Button status (see below table)                                                     |
| 7, 8, 9            | --                    | Left analog stick data                                                              |
| 10, 11, 12          | --                    | Right analog stick data                                                             |
| 13                 | `x70`, `xC0`, `xB0`   | Vibrator input report. Decides if next vibration pattern should be sent.            |
| 14  (ID `x21`)     | `x00`, `x80`, `x90`, `x82`| ACK byte for subcmd reply. ACK: MSB is `1`, NACK: MSB is `0`. If reply is ACK and has data, `byte12 & 0x7F` gives as the type of data. If simple ACK or NACK, the data type portion is `x00` |
| 15  (ID `x21`)     | `x02`, `x10`, `x03`   | Reply-to subcommand ID. The subcommand ID is used as-is.                            |
| 16-50  (ID `x21`)  | --                    | Subcommand reply data. Max 35 bytes (excludes 2 byte subcmd ack above).             |
| 16-50  (ID `x23`)  | --                    | NFC/IR MCU FW update input report. Max 37 bytes.                                    |
| 14-49  (ID `x30`, `x31`, `x32`, `x33`) | -- | 6-Axis data. 3 frames of 2 groups of 3 Int16LE each. Group is Acc followed by Gyro. |
| 50-362  (ID `x31`) | --                    | NFC/IR data input report. Max 313 bytes.                                            |

#### Standard input report - buttons
| Byte       | Bit `x01` | `x02` | `x04`    | `x08`    | `x16` | `x32`    | `x64` | `x128`        |
|:----------:|:---------:|:-----:|:--------:|:--------:|:-----:|:--------:|:-----:|:-------------:|
| 1 (Right)  | A         | X     | B        | Y        | SL    | SR       |       |               |
| 2 (Shared) | Minus     | Plus  | L Stick  | R Stick  | Home  | Capture  | L, R  | ZL, ZR        |
| 1 (Left)   | Left      | Down  | Up       | Right    | SL    | SR       |       |               |

#### Standard input report - Stick data

| Byte       | Bit `x00` | `x01` | `x02`    | `x03`    | `x04` | `x05`    | `x06` | `x07`         | `x08`    |
|:----------:|:---------:|:-----:|:--------:|:--------:|:-----:|:--------:|:-----:|:-------------:|:--------:|
| 3 (Right)  | 0         | 315   | 270      | 225      | 180   | 135      | 90    | 45            | NEUTRAL  |
| 3 (Left)   | 180       | 135   | 90       | 45       | 0     | 315      | 270   | 225           | NEUTRAL  |

The numbers are represented in degrees of a unit circle (Anticlockwise, 0-EAST, 180-WEST)
