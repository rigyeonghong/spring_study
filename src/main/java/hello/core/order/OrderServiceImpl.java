package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
   // 고정할인 구현체
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 이게 왜 설계가 잘됬냐 : 할인에 대해 나는 모르겠어 orderservice는 니가 알아서 해봐 = 단일체계원
        // 만약 설계가 잘못되고 discountPolicy 없었다면 orderservice에서 할인 관련 변경 들어와도 고쳐야함
        // 이부분은 고민해도됨 grade만 넘길까 member 전체를 넘길까
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
