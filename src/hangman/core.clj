(ns hangman.core
  (:gen-class))

(def total-de-vidas 6)
(def palavra-secreta "TOP")

(defn perdeu [] (println "Você perdeu"))
(defn ganhou [] (println "Aeeee você conseguiu! "))

(defn letras-faltantes [palavra conjunto]
  (remove (fn[letra] (contains? conjunto (str letra))) palavra))

(defn acertou-a-palavra-toda? [palavra conjunto]
  (empty? (letras-faltantes palavra conjunto))) ;;se estiver vazia, é pq acertou.

(defn le-letra! [] (read-line))

(defn acertou? [chute palavra]
  (.contains palavra chute))

(defn imprime-foca [vidas palavra conjunto]
  (println "Vidas " vidas)
  (doseq [letra (seq palavra)]
    (if (contains? conjunto (str letra))
      (print letra "")
      (print "_" " ")))
  (println))

(defn jogo [vidas palavra conjunto]
  (imprime-foca vidas palavra conjunto)
  (cond
    (= vidas 0) (perdeu)
    (acertou-a-palavra-toda? palavra conjunto)(ganhou)
    :else
    (let [chute (le-letra!)]
      (if(acertou? chute palavra) ;;verifica se na palavra tem a letra do chute.
        (do
          (println "Acertou a letra")
          ;;recur resolve o problema de empilhamento, com recursao de cauda.
          (recur vidas palavra (conj conjunto chute))) ;;criou um novo conjunto.
        (do
          (println "Errou a letra")
          ;;recur resolve o problema de empilhamento, com recursao de cauda.
          (recur (dec vidas) palavra conjunto))) ;;repete o jogo com menos vidas
      )))

(defn comeca-o-jogo [] (jogo total-de-vidas palavra-secreta #{}))

(defn -main [& args]
  (comeca-o-jogo))
