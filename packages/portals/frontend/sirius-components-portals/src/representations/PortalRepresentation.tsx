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
import Typography from '@material-ui/core/Typography';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import { useCallback, useEffect, useRef, useState } from 'react';
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
  dropArea: {
    backgroundColor: theme.palette.grey[200],
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.grey[400],
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
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
  const { addPortalView, removePortalView, layoutPortal, layoutInProgress } = usePortalMutations(
    editingContextId,
    representationId
  );

  const [mode, setMode] = useState<PortalRepresentationMode | null>(null);
  const portalHasViews: boolean | null = portal && portal.views.length > 0;
  useEffect(() => {
    if (readOnly) {
      setMode('read-only');
    } else if (portal !== null && (mode === null || mode === 'read-only')) {
      // We pass here when we have received the portal (it is non-null) but:
      // - either we have not yet decided what the initial/default mode should be
      //   (mode is still null);
      // - or we were previously in read-only mode and become editable again.
      setMode(portalHasViews ? 'direct' : 'edit');
    }
  }, [readOnly, portal, portalHasViews]);

  const portalIncludesRepresentation = (representationId: string) => {
    return portal?.views.find((view) => view?.representationMetadata?.id === representationId);
  };

  const handleDrop = (event: Event, item: LayoutItem) => {
    event.preventDefault();
    if (mode === 'read-only') {
      return;
    }
    const droppedRepresentationId: string | null = getFirstDroppedElementId(event);
    if (droppedRepresentationId === null) {
      addErrorMessage('Invalid drop.');
    } else if (portalIncludesRepresentation(droppedRepresentationId)) {
      addErrorMessage('The representation is already present in this portal.');
    } else {
      addPortalView(droppedRepresentationId, item.x, item.y, item.w, item.h);
    }
  };

  const handleDeleteView = (view: GQLPortalView) => {
    removePortalView(view.id);
  };

  const handleLayoutChange = (layout: Layout) => {
    if (!layoutInProgress) {
      const newLayoutData: GQLLayoutPortalLayoutData[] = layout.map((layoutItem) => ({
        portalViewId: layoutItem.i,
        x: layoutItem.x,
        y: layoutItem.y,
        width: layoutItem.w,
        height: layoutItem.h,
      }));
      layoutPortal(newLayoutData);
    }
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

  let items: JSX.Element[] = [
    <div
      key="drop-area"
      className={classes.dropArea}
      data-testid="portal-drop-area"
      data-grid={{
        x: 0,
        y: 0,
        w: 10,
        h: 20,
        static: true,
      }}>
      <AddIcon fontSize="large" />
      <Typography variant="subtitle2" align="center">
        Add representations by dropping them from the explorer
      </Typography>
    </div>,
  ];
  if (mode && portal && portal.views.length > 0) {
    items = portal.views
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
                representation={view.representationMetadata}
                portalMode={mode}
                onDelete={() => {
                  if (mode !== 'read-only') {
                    handleDeleteView(view);
                  }
                }}
              />
            </div>
          );
        } else {
          return <div key={view.id} />;
        }
      });
  }

  if (message) {
    return <div>{message}</div>;
  }
  if (complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!portal || !mode) {
    return <div></div>;
  }

  const cellSize: number = theme.spacing(3);
  return (
    <div className={classes.portalRepresentationArea} ref={domNode} data-representation-kind="portal">
      <PortalToolbar
        editingContextId={editingContextId}
        representationId={representationId}
        fullscreenNode={domNode}
        portalMode={mode}
        setPortalMode={(newMode) => setMode(newMode)}
      />
      <SelectionContext.Provider value={{ selection, setSelection: nonPropagatingSetSelection }}>
        <ResponsiveGridLayout
          data-testid="portal-grid-layout"
          className="layout"
          rowHeight={cellSize}
          autoSize={true}
          margin={[theme.spacing(1), theme.spacing(1)]}
          compactType={portalHasViews ? 'vertical' : null}
          draggableHandle=".draggable"
          isDraggable={mode === 'edit'}
          isResizable={mode === 'edit'}
          isDroppable={mode === 'edit'}
          allowOverlap={!portalHasViews}
          droppingItem={{ i: 'drop-item', w: 4, h: 6 }}
          onDrop={(_layout: Layout, item: LayoutItem, event: Event) => {
            if (mode !== 'read-only') {
              handleDrop(event, item);
            }
          }}
          onLayoutChange={handleLayoutChange}>
          {items}
        </ResponsiveGridLayout>
      </SelectionContext.Provider>
    </div>
  );
};
