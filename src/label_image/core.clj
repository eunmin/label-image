(ns label-image.core
  (:gen-class)
  (:require [label-image.tf.tensorflow :refer [version]])
  (:import java.nio.file.Paths))

(def ^:private url "https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip")

(defn print-usage []
  (println "Java program that uses a pre-trained Inception model (http://arxiv.org/abs/1512.00567)")
  (println "to label JPEG images.")
  (println "TensorFlow version:" version)
  (println)
  (println "Usage: label_image <model dir> <image file>")
  (println)
  (println "Where:")
  (println "<model dir> is a directory containing the unzipped contents of the inception model")
  (println "            (from" url ")")
  (println "<image file> is the path to a JPEG image file"))

(defn read-all-bytes-or-exit [path])

(defn read-all-lines-or-exit [path])

(defn construct-and-execute-graph-to-normalize-image [image-bytes])

(defn execute-inception-graph [graph-def image])

(defn max-index [label-probabilities])

(defn -main [& [model-dir image-file]]
  (if (some nil? [model-dir image-file])
    (print-usage)
    (let [graph-def (read-all-bytes-or-exit (Paths/get model-dir "tensorflow_inception_graph.pb"))
          labels (read-all-lines-or-exit (Paths/get model-dir "imagenet_comp_graph_label_strings.txt"))
          image-bytes (read-all-bytes-or-exit (Paths/get image-file))
          ^Tensor image (construct-and-execute-graph-to-normalize-image image-bytes)
          label-probabilities (execute-inception-graph graph-def image)
          best-label-idx (max-index label-probabilities)]
      (println (format "BEST MATCH: %s (%.2f%% likely)"
                 (nth labels best-label-idx)
                 (* (nth label-probabilities best-label-idx) 100.0))))))
