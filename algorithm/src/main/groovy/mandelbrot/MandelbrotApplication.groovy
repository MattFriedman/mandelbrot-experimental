package mandelbrot
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-06.
 */

class MandelbrotApplication {

    public static void main(String[] args) {

        final int maxIterations = 1000

        def width = 640
        def height = width

//        def realStart = -2.0
//        def realEnd = 1.0
//        def imagStart = -1.5
//        def imageEnd = 1.5


        /*


        Center X Coordinate	-0.13856524454488
Center Y Coordinate	-0.64935990748190
Size	.00000000045
Max Iterations Per Pixel	5000000000
         */



        def X = -0.7615754595
        def Y = -0.0847584970
        def zoom = 1000000005


        X = -0.235125
        Y = 0.827215
        zoom = 4000000

        X = -0.722
        Y = 0.246
        zoom = 1/0.019 * 10

        X = -1.25066
        Y = 0.02012
        zoom = 1.7 * 10

        def realStart = X - 2/zoom
        def realEnd = X + 1/zoom
        def imagStart = Y - 1.5/zoom
        def imageEnd = Y + 1.5/zoom

        ///-1.108,0.230

        def grid = new Grid2<Double>(width, height, 1, imageEnd, imagStart, realEnd, realStart)
//        def grid = new Grid2<Double>(width, height, 1, 1.5, -1.5, 1.0, -2.0)
//−0.1011 + 0.9563i.
        def processor = new MandelbrotProcessor<Double>(grid, new DoublePrecisionFactory())

        processor.process(maxIterations)

        System.exit(1)
    }
}








