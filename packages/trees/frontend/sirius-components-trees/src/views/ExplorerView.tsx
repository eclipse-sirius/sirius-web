/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
  SynchronizeWithSelectionContextProvider,
  WorkbenchViewComponentProps,
  useSynchronizedWithSelection,
} from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useContext, useEffect, useRef, useState } from 'react';
import { TreeToolBar } from '../toolbar/TreeToolBar';
import { TreeToolBarContext } from '../toolbar/TreeToolBarContext';
import { TreeToolBarContextValue } from '../toolbar/TreeToolBarContext.types';
import { FilterBar } from '../trees/FilterBar';
import { ExplorerViewState } from './ExplorerView.types';
import { useExplorerViewConfiguration } from './ExplorerViewConfiguration';
import { TreeView } from './TreeView';

const useStyles = makeStyles((theme) => ({
  treeView: {
    display: 'flex',
    flexDirection: 'column',
  },
  treeContent: {
    paddingTop: theme.spacing(1),
    flexGrow: 1,
    overflow: 'auto',
  },
}));

export const ExplorerView = (props: WorkbenchViewComponentProps) => {
  return (
    <SynchronizeWithSelectionContextProvider>
      <ExplorerViewContent {...props} />
    </SynchronizeWithSelectionContextProvider>
  );
};

const ExplorerViewContent = (props: WorkbenchViewComponentProps) => {
  const styles = useStyles();
  const { converter } = useExplorerViewConfiguration();
  const initialState: ExplorerViewState = {
    filterBar: false,
    filterBarText: '',
    filterBarTreeFiltering: false,
  };
  const { isSynchronized } = useSynchronizedWithSelection();
  const [state, setState] = useState<ExplorerViewState>(initialState);
  const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
    (contribution) => contribution.props.component
  );

  const treeElement = useRef<HTMLDivElement>(null);
  useEffect(() => {
    const downHandler = (event) => {
      if ((event.ctrlKey === true || event.metaKey === true) && event.key === 'f' && event.target.tagName !== 'INPUT') {
        event.preventDefault();
        setState((prevState) => {
          return { ...prevState, filterBar: true, filterBarText: '', filterBarTreeFiltering: false };
        });
      }
    };
    const element = treeElement?.current;
    if (element) {
      element.addEventListener('keydown', downHandler);

      return () => {
        element.removeEventListener('keydown', downHandler);
      };
    }
    return null;
  }, [treeElement]);

  let filterBar: JSX.Element;
  if (state.filterBar) {
    filterBar = (
      <FilterBar
        onTextChange={(event) => {
          const {
            target: { value },
          } = event;
          setState((prevState) => {
            return { ...prevState, filterBarText: value };
          });
        }}
        onFilterButtonClick={(enabled) =>
          setState((prevState) => {
            return { ...prevState, filterBarTreeFiltering: enabled };
          })
        }
        onClose={() =>
          setState((prevState) => {
            return { ...prevState, filterBar: false, filterBarText: '', filterBarTreeFiltering: false };
          })
        }
      />
    );
  }
  return (
    <div className={styles.treeView} ref={treeElement}>
      <TreeToolBar {...props} treeToolBarContributionComponents={treeToolBarContributionComponents} />
      <div className={styles.treeContent}>
        {filterBar}
        <TreeView
          {...props}
          treeId="explorer://"
          enableMultiSelection={true}
          synchronizedWithSelection={isSynchronized}
          textToHighlight={state.filterBarText}
          textToFilter={state.filterBarTreeFiltering ? state.filterBarText : null}
          converter={converter}
        />
      </div>
    </div>
  );
};
