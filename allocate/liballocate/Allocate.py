import math

def allocate(quantity, weights):
    if not weights: return weights

    # because of this ordering under same conditions, the last gets more
    indexed_weights = sorted([(weights[i], i) for i in range(len(weights))], reverse=True)
    allocation      = [0 for _ in range(len(weights))]
    total_weight    = sum(weights)

    def _allocate_next(total, remains, ws, allocation):
        if len(ws) == 1:
            (_,i) = ws.pop(0)
            allocation[i] = remains
            return allocation
        else:
            (w,i) = ws.pop(0)
            p     = w / float(total) if total else 0
            n     = math.ceil(remains * p)
            allocation[i] = n
            return _allocate_next(total-w, remains-n, ws, allocation)

    return _allocate_next(total_weight, quantity, indexed_weights, allocation)
