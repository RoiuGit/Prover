public class SubProof extends Proof{

    SubProof(int startingIndex, Formula assumption, int assumptionDepth){
        this.startingIndex = startingIndex;
        endingIndex = startingIndex - 1;
        append(assumption);
        this.assumptionDepth = assumptionDepth;
        isClosed = false;
    }

}