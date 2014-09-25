import math

def allocate(quantity, weights):
	if not weights: return weights

	def _allocate_next(total, remains, ws, acum):
		if len(ws) == 1:
			acum.append(remains)
			return acum
		else:
			w = ws.pop(0)
			p = w / float(total) if total else 0
			n = math.ceil(remains * p)
			acum.append(n)
			return _allocate_next(total-w, remains-n, ws, acum)

	return _allocate_next(sum(weights), quantity, weights, [])
