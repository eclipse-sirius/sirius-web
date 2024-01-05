/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  DRAG_SOURCES_TYPE,
  RepresentationComponentProps,
  Selection,
  SelectionContext,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import { useCallback, useMemo, useRef, useState } from 'react';
import GridLayout, { Layout, LayoutItem, WidthProvider } from 'react-grid-layout';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import { usePortal } from '../hooks/usePortal';
import { GQLPortalView } from '../hooks/usePortal.types';
import { usePortalMutations } from '../hooks/usePortalMutations';
import { GQLLayoutPortalLayoutData } from '../hooks/usePortalMutations.types';
import { PortalRepresentationMode } from './PortalRepresentation.types';
import { PortalToolbar } from './PortalToolbar';
import { RepresentationFrame } from './RepresentationFrame';

const usePortalRepresentationStyles = makeStyles((theme) => ({
  portalRepresentationArea: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr',
    backgroundColor: theme.palette.background.default,
  },
}));

const ResponsiveGridLayout = WidthProvider(GridLayout);

const getFirstDroppedElementId = (event): string | null => {
  const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
  if (dragSourcesStringified) {
    const sources = JSON.parse(dragSourcesStringified);
    if (Array.isArray(sources)) {
      const sourceIds: string[] = sources.filter((source) => source?.id).map((source) => source.id);
      if (sourceIds.length > 0) {
        return sourceIds[0] || null;
      }
    }
  }
  return null;
};

export const PortalRepresentation = ({
  editingContextId,
  representationId,
  readOnly,
}: RepresentationComponentProps) => {
  const theme = useTheme();
  const classes = usePortalRepresentationStyles();
  const domNode = useRef<HTMLDivElement>(null);
  const { addErrorMessage } = useMultiToast();
  const { selection, setSelection } = useSelection();
  const { portal, complete, message } = usePortal(editingContextId, representationId);
  const { addPortalView, removePortalView, layoutPortal } = usePortalMutations(editingContextId, representationId);
  const [mode, setMode] = useState<PortalRepresentationMode>('edit');

  const portalIncludesRepresentation = (representationId: string) => {
    return portal?.views.find((view) => view?.representationMetadata?.id === representationId);
  };

  const handleDrop = (event: Event) => {
    event.preventDefault();
    const droppedRepresentationId: string | null = getFirstDroppedElementId(event);
    if (droppedRepresentationId === null) {
      addErrorMessage('Invalid drop.');
    } else if (portalIncludesRepresentation(droppedRepresentationId)) {
      addErrorMessage('The representation is already present in this portal.');
    } else {
      addPortalView(droppedRepresentationId);
    }
  };

  const handleDeleteView = (view: GQLPortalView) => {
    removePortalView(view.id);
  };

  const handleLayoutChange = (layout: Layout) => {
    const newLayoutData: GQLLayoutPortalLayoutData[] = layout.map((layoutItem) => ({
      portalViewId: layoutItem.i,
      x: layoutItem.x,
      y: layoutItem.y,
      width: layoutItem.w,
      height: layoutItem.h,
    }));
    layoutPortal(newLayoutData);
  };

  /*
   * This is needed to avoid leaving the portal when an embedded representation
   * is selected from inside its view (typically, when the user clicks in the
   * background of a diagram).
   */
  const nonPropagatingSetSelection = useCallback(
    (selection: Selection) => {
      const filteredEntries = selection.entries.filter(
        (entry) => !entry.kind.startsWith('siriusComponents://representation')
      );
      if (filteredEntries.length > 0) {
        setSelection({ entries: filteredEntries });
      }
    },
    [setSelection]
  );

  let content: JSX.Element | null = null;

  const children: JSX.Element[] | null = useMemo(() => {
    if (portal) {
      return portal.views
        .filter((view) => view?.representationMetadata?.id !== representationId)
        .map((view) => {
          const layout = portal.layoutData?.find((viewLayoutData) => viewLayoutData.portalViewId === view.id);
          if (layout && view.representationMetadata) {
            return (
              <div
                key={view.id}
                data-grid={{
                  x: layout.x,
                  y: layout.y,
                  w: layout.width,
                  h: layout.height,
                  static: mode === 'direct',
                }}
                style={{ display: 'grid' }}>
                <RepresentationFrame
                  editingContextId={editingContextId}
                  readOnly={readOnly}
                  representation={view.representationMetadata}
                  breadcrumbs={view.representationMetadata.breadcrumbs}
                  portalMode={mode}
                  onDelete={() => handleDeleteView(view)}
                />
              </div>
            );
          } else {
            return <div key={view.id} />;
          }
        });
    } else {
      return null;
    }
  }, [portal, mode]);

  if (children) {
    content = (
      <SelectionContext.Provider value={{ selection, setSelection: nonPropagatingSetSelection }}>
        <ResponsiveGridLayout
          className="layout"
          rowHeight={theme.spacing(3)}
          width={1200}
          autoSize={true}
          margin={[theme.spacing(1), theme.spacing(1)]}
          draggableHandle=".draggable"
          isDroppable={mode === 'edit'}
          droppingItem={{ i: 'drop-item', w: 4, h: 3 }}
          onDrop={(_layout: Layout, _item: LayoutItem, event: Event) => {
            // TODO: consider the initial layout implied by the drop position
            handleDrop(event);
          }}
          onLayoutChange={handleLayoutChange}>
          {children}
        </ResponsiveGridLayout>
      </SelectionContext.Provider>
    );
  } else {
    content = <div />;
  }

  if (message) {
    return <div>{message}</div>;
  }
  if (complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!portal) {
    return <div></div>;
  }

  return (
    <div className={classes.portalRepresentationArea} ref={domNode}>
      <div>
        <PortalToolbar fullscreenNode={domNode} portalMode={mode} setPortalMode={(newMode) => setMode(newMode)} />
      </div>
      {content}
    </div>
  );
};
