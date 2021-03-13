package in.omgss.interfaces;

public interface ProductItemClickListener {
    void onItemClicked(int sectionPosition, int itemPosition);

    void onAddToWishListClicked(int sectionPosition, int itemPosition);

    void onAddToCartClicked(int sectionPosition, int itemPosition);
}
