# Readable Code

## 객체 설계하기 #10 - Cell 리팩토링

이 커밋의 핵심은 **Cell 클래스가 자신의 상태를 직접 관리하도록 리팩토링**한 것입니다.

---

### 변경 전 (Before) - 빈약한 객체

`Cell`은 단순히 **표시할 문자열(sign)만 저장**하는 값 객체였습니다:

```java
// Cell은 그냥 "표시 문자"를 감싸는 껍데기
private final String sign;

// 게임 상태는 별도의 2차원 배열들이 관리
private static final Integer[][] NEARBY_LAND_MINE_COUNTS = ...;
private static final boolean[][] LAND_MINES = ...;
```

- `Cell.ofFlag()`, `Cell.ofLandMine()` 등은 **문자열만 다른 Cell을 생성**
- 지뢰 여부, 주변 지뢰 수는 **별도 배열**(`LAND_MINES`, `NEARBY_LAND_MINE_COUNTS`)이 관리
- 게임 로직이 `MinesweeperGame`에 집중됨

---

### 변경 후 (After) - 풍부한 도메인 객체

`Cell`이 **자신의 모든 상태를 직접 보유**:

```java
private int nearByLandMineCount;   // 주변 지뢰 수
private boolean isLandMine;         // 지뢰 여부
private boolean isFlagged;          // 깃발 표시 여부
private boolean isOpened;           // 열렸는지 여부
```

---

### 주요 변경 사항 비교

| 항목 | Before | After |
|------|--------|-------|
| Cell 생성 | `Cell.ofClosed()` (문자열 "□") | `Cell.create()` (상태 초기화) |
| 지뢰 표시 | `LAND_MINES[row][col] = true` | `cell.turnOnLandMine()` |
| 주변 지뢰 수 | `NEARBY_LAND_MINE_COUNTS[row][col] = count` | `cell.updateNearByLandMineCount(count)` |
| 지뢰 확인 | `LAND_MINES[row][col]` 조회 | `cell.isLandMine()` |
| 셀 열기 | `BOARD[row][col] = Cell.ofOpened()` | `cell.opened()` |
| 깃발 꽂기 | `BOARD[row][col] = Cell.ofFlag()` | `cell.flag()` |
| 표시 문자 | 생성 시 결정됨 | `getSign()`이 **상태 기반으로 동적 결정** |

---

### `getSign()` 로직 변화

**Before**: 생성 시점에 문자열이 결정됨

**After**: 현재 상태를 보고 동적으로 결정

```java
public String getSign() {
    if (isOpened) {
        if (isLandMine) return "☼";
        if (hasLandMineCount()) return String.valueOf(nearByLandMineCount);
        return "■";  // 빈 칸
    }
    if (isFlagged) return "⚑";
    return "□";  // 미확인 셀
}
```

---

### 설계 의도

1. **정보 은닉(Encapsulation)**: 지뢰/주변 지뢰 수 등의 정보가 Cell 내부로 캡슐화
2. **별도 배열 제거**: `LAND_MINES`, `NEARBY_LAND_MINE_COUNTS` 배열 삭제 → `BOARD` 하나로 통합
3. **객체에게 메시지 보내기**: `cell.flag()`, `cell.opened()` 처럼 객체에게 행동을 요청
4. **Tell, Don't Ask**: 상태를 꺼내서 판단하지 않고, 객체가 스스로 판단 (`cell.isChecked()`)

---

## Enum의 특성과 활용 #3 - 그리는 책임을 ConsoleOutputHandler로 이동

이 커밋의 핵심은 **Cell이 "어떻게 그릴지"를 결정하지 않고, 출력 담당 객체(ConsoleOutputHandler)가 결정하도록 책임을 분리**한 것입니다.

---

### 변경 전 (Before) - Cell이 출력 형식까지 결정

```java
// Cell 인터페이스에 출력용 상수가 존재
public interface Cell {
    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGN = "□";
    
    String getSign();  // Cell이 직접 문자열 반환
}

// 각 Cell 구현체가 어떤 기호로 보여줄지 결정
public class LandMineCell implements Cell {
    private static final String LAND_MINE_SIGN = "☼";
    
    @Override
    public String getSign() {
        if (cellState.isOpened()) return LAND_MINE_SIGN;
        if (cellState.isFlagged()) return FLAG_SIGN;
        return UNCHECKED_SIGN;
    }
}
```

**문제점**: 도메인 객체(Cell)가 UI 표현 방식(콘솔 기호)을 알고 있음

---

### 변경 후 (After) - Cell은 상태만, 출력은 OutputHandler가

#### 1. CellSnapshotStatus (Enum) 도입

```java
public enum CellSnapshotStatus {
    EMPTY("빈 셀"),
    FLAG("깃발"),
    LAND_MINE("지뢰"),
    NUMBER("숫자"),
    UNCHECKED("확인 전");
}
```

#### 2. CellSnapshot (DTO) 도입

```java
public class CellSnapshot {
    private final CellSnapshotStatus status;
    private final int nearByLandMineCount;
    
    public static CellSnapshot ofEmpty() { ... }
    public static CellSnapshot ofFlag() { ... }
    public static CellSnapshot ofLandMine() { ... }
    public static CellSnapshot ofNumber(int count) { ... }
    public static CellSnapshot ofUnchecked() { ... }
}
```

#### 3. Cell은 Snapshot만 반환

```java
public interface Cell {
    CellSnapshot getSnapshot();  // 문자열 대신 상태 객체 반환
}

public class LandMineCell implements Cell {
    @Override
    public CellSnapshot getSnapshot() {
        if (cellState.isOpened()) return CellSnapshot.ofLandMine();
        if (cellState.isFlagged()) return CellSnapshot.ofFlag();
        return CellSnapshot.ofUnchecked();
    }
}
```

#### 4. ConsoleOutputHandler가 기호 결정

```java
public class ConsoleOutputHandler implements OutputHandler {
    private static final String LAND_MINE_SIGN = "☼";
    private static final String EMPTY_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String UNCHECKED_SIGN = "□";
    
    private String decideCellSignFrom(CellSnapshot snapshot) {
        CellSnapshotStatus status = snapshot.getStatus();
        if (status == CellSnapshotStatus.EMPTY) return EMPTY_SIGN;
        if (status == CellSnapshotStatus.FLAG) return FLAG_SIGN;
        if (status == CellSnapshotStatus.NUMBER) return String.valueOf(snapshot.getNearByLandMineCount());
        if (status == CellSnapshotStatus.UNCHECKED) return UNCHECKED_SIGN;
        if (status == CellSnapshotStatus.LAND_MINE) return LAND_MINE_SIGN;
        throw new IllegalStateException("Unknown status: " + status);
    }
}
```

---

### 주요 변경 사항 비교

| 항목 | Before | After |
|------|--------|-------|
| Cell 반환값 | `String getSign()` | `CellSnapshot getSnapshot()` |
| 출력 기호 상수 위치 | `Cell` 인터페이스 | `ConsoleOutputHandler` |
| 기호 결정 책임 | 각 Cell 구현체 | `ConsoleOutputHandler` |

---

### 설계 의도

1. **관심사의 분리 (Separation of Concerns)**
   - Cell: 게임 로직과 상태 관리에만 집중
   - ConsoleOutputHandler: 출력 형식 결정에만 집중

2. **출력 방식 교체 용이**
   - 콘솔 → GUI로 변경 시 `Cell` 수정 불필요
   - 새로운 `GuiOutputHandler`만 구현하면 됨

3. **Enum을 통한 타입 안전성**
   - 문자열 대신 `CellSnapshotStatus` Enum 사용
   - 컴파일 타임에 오류 검출 가능

4. **Snapshot 패턴**
   - 현재 상태의 "스냅샷"을 전달하여 불변성 확보
   - Cell 내부 상태를 직접 노출하지 않음

---

## 다형성 활용하기 #1 - CellSignProvider 구조 도입

이 커밋은 **if-else 분기를 다형성으로 대체하기 위한 구조를 준비**한 것입니다.

---

### 문제 상황 - ConsoleOutputHandler의 if-else 체인

이전 커밋에서 `ConsoleOutputHandler`에 다음과 같은 코드가 있었습니다:

```java
private String decideCellSignFrom(CellSnapshot snapshot) {
    CellSnapshotStatus status = snapshot.getStatus();
    if (status == CellSnapshotStatus.EMPTY) return EMPTY_SIGN;
    if (status == CellSnapshotStatus.FLAG) return FLAG_SIGN;
    if (status == CellSnapshotStatus.NUMBER) return String.valueOf(snapshot.getNearByLandMineCount());
    if (status == CellSnapshotStatus.UNCHECKED) return UNCHECKED_SIGN;
    if (status == CellSnapshotStatus.LAND_MINE) return LAND_MINE_SIGN;
    throw new IllegalStateException("Unknown status: " + status);
}
```

**문제점**: 새로운 셀 상태가 추가되면 if-else를 수정해야 함 (OCP 위반)

---

### 해결 - 인터페이스 + 각 상태별 구현체

#### 1. CellSignProvidable 인터페이스

```java
public interface CellSignProvidable {
    String provide(CellSnapshot cellSnapshot);
}
```

#### 2. 각 상태별 Provider 구현체

```java
public class EmptyCellSignProvider implements CellSignProvidable {
    private static final String EMPTY_SIGN = "■";
    
    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return EMPTY_SIGN;
    }
}

public class FlagCellSignProvider implements CellSignProvidable {
    private static final String FLAG_SIGN = "⚑";
    
    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return FLAG_SIGN;
    }
}

public class LandMineCellSignProvider implements CellSignProvidable {
    private static final String LAND_MINE_SIGN = "☼";
    
    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return LAND_MINE_SIGN;
    }
}

public class NumberCellSignProvider implements CellSignProvidable {
    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return String.valueOf(cellSnapshot.getNearByLandMineCount());
    }
}

public class UncheckedCellSignProvider implements CellSignProvidable {
    private static final String UNCHECKED_SIGN = "□";
    
    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return UNCHECKED_SIGN;
    }
}
```

---

### 구조 다이어그램

```
CellSignProvidable (인터페이스)
        │
        ├── EmptyCellSignProvider      → "■"
        ├── FlagCellSignProvider       → "⚑"
        ├── LandMineCellSignProvider   → "☼"
        ├── NumberCellSignProvider     → "1"~"8"
        └── UncheckedCellSignProvider  → "□"
```

---

### 설계 의도

1. **OCP (Open-Closed Principle)**
   - 새로운 셀 상태 추가 시 기존 코드 수정 없이 새 Provider만 추가
   
2. **SRP (Single Responsibility Principle)**
   - 각 Provider는 하나의 상태에 대한 기호만 책임짐

3. **다형성을 통한 분기 제거**
   - if-else 체인 → 인터페이스 호출로 대체 예정 (다음 커밋에서)

4. **전략 패턴 (Strategy Pattern) 준비**
   - 상황에 맞는 Provider를 선택하여 사용하는 구조
