package api;
/**
 * Η κλάση QuantityType ορίζει τους διαφορετικούς τύπους ποσότητας που μπορεί να έχει ένα προϊόν.
 * Οι διαθέσιμοι τύποι είναι τα κιλά(kg) και τα τεμάχια.
 */
public enum QuantityType {
    KILOGRAMS, PIECES;

    @Override
    public String toString() {
        switch (this) {
            case KILOGRAMS: return "kg";
            case PIECES: return " τεμάχια";
        }
        return super.toString();
    }
}
