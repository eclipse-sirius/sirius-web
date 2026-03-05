/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ApolloProvider } from "@apollo/client";
import {
  SelectionContextProvider,
  ServerContext,
} from "@eclipse-sirius/sirius-components-core";
import {
  DiagramRepresentation,
  type GQLDiagram,
  type WorkbenchDiagramRepresentationHandle,
} from "@eclipse-sirius/sirius-components-diagrams";
import { type LayoutOptions } from "elkjs/lib/elk-api";
import i18n from "i18next";
import { useEffect, useRef } from "react";
import { I18nextProvider, initReactI18next } from "react-i18next";
import "reactflow/dist/style.css";
import "./Global.css";
import type { DiagramStoryArgs } from "./layoutConfigurations";
import { useClient } from "./useClient";

if (!i18n.isInitialized) {
  i18n.use(initReactI18next).init({});
}

export const DiagramStoryWrapper = ({
  args,
  diagram,
  layoutOptions,
}: {
  args: DiagramStoryArgs;
  diagram: () => GQLDiagram;
  layoutOptions: LayoutOptions;
}) => {
  const diagramHandleRef = useRef<WorkbenchDiagramRepresentationHandle>(null);
  const client = useClient(diagram());

  useEffect(() => {
    if (!args.autoLayout) return;
    setTimeout(() => {
      if (diagramHandleRef.current?.applyLayout) {
        diagramHandleRef.current.applyLayout(layoutOptions);
      }
    }, 1000);
  }, [args.autoLayout, layoutOptions]);
  return (
    <ApolloProvider client={client}>
      <I18nextProvider i18n={i18n}>
        <ServerContext.Provider value={{ httpOrigin: "http://localhost" }}>
          <SelectionContextProvider initialSelection={{ entries: [] }}>
            <div className={"diagramRepresentationContainer"}>
              <DiagramRepresentation {...args} ref={diagramHandleRef} />
            </div>
          </SelectionContextProvider>
        </ServerContext.Provider>
      </I18nextProvider>
    </ApolloProvider>
  );
};
