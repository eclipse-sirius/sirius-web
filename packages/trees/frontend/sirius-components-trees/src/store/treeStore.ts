import { create } from 'zustand';

export type StoreType = {
  expanded: string[];
  maxDepth: number;
  updateMaxDepth: (maxDepth) => void;
  updateExpended: (newExpended) => void;
  onExpand: (id, depth) => void;
};

export const useTreeStore = create<StoreType>((set, get) => ({
  expanded: [],
  maxDepth: 0,
  updateMaxDepth: (newMaxDepth) => set(() => ({ maxDepth: newMaxDepth })),
  updateExpended: (newExpended) => set(() => ({ expanded: newExpended })),
  onExpand: (id, depth) => {
    console.log('onExpand ' + id + ' - ' + depth);
    const currentExpanded = get().expanded;
    const currentMaxDepth = get().maxDepth;
    if (currentExpanded.includes(id)) {
      const newExpanded = [...currentExpanded];
      newExpanded.splice(newExpanded.indexOf(id), 1);
      set((state) => ({ ...state, expanded: newExpanded, maxDepth: Math.max(currentMaxDepth, depth) }));
    } else {
      set((state) => ({ ...state, expanded: [...currentExpanded, id], maxDepth: Math.max(currentMaxDepth, depth) }));
    }
  },
}));
