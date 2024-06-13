/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
  RepresentationMetadata,
  Selection,
  SelectionContextProvider,
  Workbench,
} from '@eclipse-sirius/sirius-components-core';
import { useMachine } from '@xstate/react';

import {
  DiagramPaletteToolContext,
  DiagramPaletteToolContextValue,
  DiagramPaletteToolContribution,
  NodeData,
} from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLTreeItem,
  TreeItemContextMenuContext,
  TreeItemContextMenuContextValue,
  TreeItemContextMenuContribution,
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeToolBarContribution,
} from '@eclipse-sirius/sirius-components-trees';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect } from 'react';
import { generatePath, useHistory, useParams, useRouteMatch } from 'react-router-dom';
import { useNodes } from 'reactflow';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { DiagramTreeItemContextMenuContribution } from './DiagramTreeItemContextMenuContribution';
import { DocumentTreeItemContextMenuContribution } from './DocumentTreeItemContextMenuContribution';
import { EditProjectNavbar } from './EditProjectNavbar/EditProjectNavbar';
import {
  DiagramPaletteToolProviderProps,
  EditProjectViewParams,
  TreeItemContextMenuProviderProps,
  TreeToolBarProviderProps,
} from './EditProjectView.types';
import {
  EditProjectViewContext,
  EditProjectViewEvent,
  HandleFetchedProjectEvent,
  SelectRepresentationEvent,
  editProjectViewMachine,
} from './EditProjectViewMachine';
import { ObjectTreeItemContextMenuContribution } from './ObjectTreeItemContextMenuContribution';
import { ProjectContext } from './ProjectContext';
import { PapayaOperationActivityLabelDetailToolContribution } from './ToolContributions/PapayaOperationActivityLabelDetailToolContribution';
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';

const useEditProjectViewStyles = makeStyles((_) => ({
  editProjectView: {
    display: 'grid',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

export const EditProjectView = () => {
  const history = useHistory();
  const routeMatch = useRouteMatch();
  const { projectId, representationId } = useParams<EditProjectViewParams>();
  const classes = useEditProjectViewStyles();

  const [{ value, context }, dispatch] = useMachine<EditProjectViewContext, EditProjectViewEvent>(
    editProjectViewMachine
  );

  const { data } = useProjectAndRepresentationMetadata(projectId, representationId);
  useEffect(() => {
    if (data) {
      const fetchProjectEvent: HandleFetchedProjectEvent = { type: 'HANDLE_FETCHED_PROJECT', data };
      dispatch(fetchProjectEvent);
    }
  }, [data]);

  const onRepresentationSelected = (representationMetadata: RepresentationMetadata) => {
    const selectRepresentationEvent: SelectRepresentationEvent = {
      type: 'SELECT_REPRESENTATION',
      representation: representationMetadata,
    };
    dispatch(selectRepresentationEvent);
  };

  useEffect(() => {
    if (context.representation && context.representation.id !== representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: context.representation.id });
      history.push({ pathname });
    } else if (value === 'loaded' && context.representation === null && representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: null });
      history.push({ pathname });
    }
  }, [value, projectId, routeMatch, history, context.representation, representationId]);

  let content: React.ReactNode = null;

  if (value === 'loading') {
    content = <NavigationBar />;
  }

  if (value === 'missing') {
    content = (
      <>
        <NavigationBar />
        <Grid container justifyContent="center" alignItems="center">
          <Typography variant="h4" align="center" gutterBottom>
            The project does not exist
          </Typography>
        </Grid>
      </>
    );
  }

  if (value === 'loaded' && context.project) {
    const initialSelection: Selection = {
      entries: context.representation
        ? [
            {
              id: context.representation.id,
              label: context.representation.label,
              kind: context.representation.kind,
            },
          ]
        : [],
    };
    content = (
      <ProjectContext.Provider value={{ project: context.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
          <EditProjectNavbar />
          <TreeItemContextMenuProvider>
            <TreeToolBarProvider>
              <DiagramPaletteToolProvider>
                <Workbench
                  editingContextId={context.project.currentEditingContext.id}
                  initialRepresentationSelected={context.representation}
                  onRepresentationSelected={onRepresentationSelected}
                  readOnly={false}
                />
              </DiagramPaletteToolProvider>
            </TreeToolBarProvider>
          </TreeItemContextMenuProvider>
        </SelectionContextProvider>
      </ProjectContext.Provider>
    );
  }

  return <div className={classes.editProjectView}>{content}</div>;
};

const TreeItemContextMenuProvider = ({ children }: TreeItemContextMenuProviderProps) => {
  const treeItemContextMenuContributions: TreeItemContextMenuContextValue = [
    <TreeItemContextMenuContribution
      canHandle={(treeId: string, item: GQLTreeItem) =>
        treeId.startsWith('explorer://') && item.kind.startsWith('siriusWeb://document')
      }
      component={DocumentTreeItemContextMenuContribution}
    />,
    <TreeItemContextMenuContribution
      canHandle={(treeId: string, item: GQLTreeItem) =>
        treeId.startsWith('explorer://') && item.kind.startsWith('siriusComponents://semantic')
      }
      component={ObjectTreeItemContextMenuContribution}
    />,
    <TreeItemContextMenuContribution
      canHandle={(treeId: string, item: GQLTreeItem) =>
        treeId.startsWith('explorer://') && item.kind === 'siriusComponents://representation?type=Diagram'
      }
      component={DiagramTreeItemContextMenuContribution}
    />,
  ];

  return (
    <TreeItemContextMenuContext.Provider value={treeItemContextMenuContributions}>
      {children}
    </TreeItemContextMenuContext.Provider>
  );
};

const TreeToolBarProvider = ({ children }: TreeToolBarProviderProps) => {
  const treeToolBarContributions: TreeToolBarContextValue = [
    <TreeToolBarContribution component={NewDocumentModalContribution} />,
    <TreeToolBarContribution component={UploadDocumentModalContribution} />,
  ];

  return <TreeToolBarContext.Provider value={treeToolBarContributions}>{children}</TreeToolBarContext.Provider>;
};

const DiagramPaletteToolProvider = ({ children }: DiagramPaletteToolProviderProps) => {
  const diagramPaletteToolContributions: DiagramPaletteToolContextValue = [
    <DiagramPaletteToolContribution
      canHandle={(_diagramId, diagramElementId) => {
        const nodes = useNodes<NodeData>();
        const targetedNode = nodes.find((node) => node.id === diagramElementId);
        if (targetedNode) {
          return (
            targetedNode.data.targetObjectKind ===
            'siriusComponents://semantic?domain=papaya_operational_analysis&entity=OperationalActivity'
          );
        }
        return false;
      }}
      component={PapayaOperationActivityLabelDetailToolContribution}
    />,
  ];
  return (
    <DiagramPaletteToolContext.Provider value={diagramPaletteToolContributions}>
      {children}
    </DiagramPaletteToolContext.Provider>
  );
};
