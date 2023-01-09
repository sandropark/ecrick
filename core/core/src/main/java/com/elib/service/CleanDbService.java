package com.elib.service;

import com.elib.domain.Book;
import com.elib.domain.CoreBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CleanDbService {

    public Set<Book> updateToLatestBook(List<CoreBook> coreBooks) {
        Set<Book> oldBooks = new HashSet<>();
        List<CoreBook> tempGroup = new ArrayList<>();
        CoreBook latestDateBook = null;
        for (CoreBook coreBook : coreBooks) {
            if (tempGroup.isEmpty()) { // 그룹이 비어있으면 데이터를 삽입
                tempGroup.add(coreBook);
                latestDateBook = coreBook;
            } else if (coreBook.isSameGroup(tempGroup.get(0))) { // 그룹에 데이터가 있는 경우, 그룹의 데이터와 현재 책이 같은 그룹인지 확인 후 삽입
                tempGroup.add(coreBook);
                if (coreBook.laterThan(latestDateBook)) {
                    latestDateBook = coreBook;
                }
            } else {  // 다른 그룹의 데이터인 경우
                oldBooks.addAll(updateRelation(tempGroup, latestDateBook));  // 연관관계 수정 후 그룹 초기화

                tempGroup.add(coreBook);
                latestDateBook = coreBook;
            }
        }
        oldBooks.addAll(updateRelation(tempGroup, latestDateBook));
        return oldBooks;
    }

    private Set<Book> updateRelation(List<CoreBook> group, CoreBook latestDateBook) {
        Set<Book> oldBooks = new HashSet<>();   // 같은 Book을 갖고있는 CoreBook이 많기 때문에 하나만 있으면 된다.
        for (CoreBook coreBook : group) {
            if (latestDateBook.laterThan(coreBook)) {
                Book oldBook = coreBook.updateBook(latestDateBook); // 새로운 연관관계를 설정하고 이전 연관관계는 한 번에 삭제하기 위해서 컬렉션에 담는다.
                oldBooks.add(oldBook);
            }
        }

        group.clear();

        return oldBooks;
    }
}
