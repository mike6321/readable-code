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
